package com.increff.pos.dto;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.*;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;

import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import org.apache.fop.apps.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.dto.dtoHelper.OrderDtoHelper.convertOrderPojotoOrderData;
import static com.increff.pos.dto.dtoHelper.OrderItemDtoHelper.convertOrderItemPojotoOrderItemData;
import static com.increff.pos.util.HelperUtil.jaxbObjectToXML;
import static com.increff.pos.util.HelperUtil.returnFileStream;
import static java.util.Objects.isNull;

@Service
public class OrderDto {
    private final FopFactory fopFactory= FopFactory.newInstance(new File(".").toURI());

    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    BrandService brandService;
    @Autowired
    ProductService productService;


    public OrderData get(Integer id)throws ApiException{
        return convertOrderPojotoOrderData(orderService.get(id));
    }

    public List<OrderData> getAll()throws ApiException
    {
        List<OrderPojo> orderPojoList = orderService.getAll();
        List<OrderData> orderDataList = new ArrayList<>();
        for(OrderPojo orderPojo : orderPojoList)
        {
            orderDataList.add(convertOrderPojotoOrderData(orderPojo));
        }
        return orderDataList;
    }

    public ZonedDateTime add()throws ApiException
    {
        return orderService.add();
    }

    public Integer updateOrderStatusPlaced(Integer id)throws ApiException
    {
        orderService.updateOrderStatusPlaced(id);
        return id;
    }

    public byte[] getOrderInvoice(int orderId)throws ApiException, IOException, TransformerException
    {
        List<OrderItemPojo> orderItemPojoList = getOrderItemforOrderUtil(orderId);
        List<OrderItemData> orderItemDataList = new ArrayList<>();
        for(OrderItemPojo orderItemPojo : orderItemPojoList)
        {
            OrderItemData orderItemData = convertOrderItemPojotoOrderItemData(orderItemPojo);
            orderItemDataList.add(orderItemData);
        }

        ZonedDateTime time = orderService.get(orderId).getTime();
        Double total=0.0;

        for(OrderItemData orderItemData : orderItemDataList)
        {
            total+=orderItemData.getQuantity()*orderItemData.getQuantity();
        }
        OrderItemDataList orderItemDataList2=new OrderItemDataList(orderItemDataList,time,total,orderId);

        String xml = jaxbObjectToXML(orderItemDataList2);
        File xsltFile = new File("src", "invoice.xsl");
        File pdfFile = new File("src", "invoice.pdf");
        System.out.println(xml);
        convertToPDF(orderItemDataList2, xsltFile, pdfFile, xml);
        return returnFileStream();
    }

    private void convertToPDF(OrderItemDataList team, File xslt, File pdf, String xml) throws IOException, TransformerException {

        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        // configure foUserAgent as desired

        // Setup output
        OutputStream out = new java.io.FileOutputStream(pdf);
        out = new java.io.BufferedOutputStream(out);
        try {
            // Construct fop with desired output format
            Fop fop = null;
            try {
                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            } catch (FOPException e) {
                throw new RuntimeException(e);
            }

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslt));

            // Setup input for XSLT transformation
            Source src = new StreamSource(new StringReader(xml));
//                        Source src = new StreamSource(new FileInputStream(xml));
//
            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            transformer.transform(src, res);
        } catch (FOPException e) {
            throw new RuntimeException(e);
        } finally {
            out.close();
        }
    }

    public List<OrderItemPojo> getOrderItemforOrderUtil(Integer orderId)throws ApiException
    {
        OrderPojo orderPojo = orderService.get(orderId);
        if(isNull(orderPojo))
        {
            throw new ApiException("Order with this Id does not exist , id :"+orderId);
        }
        return orderItemService.selectFromOrderId(orderId);

    }

//    @Transactional(rollbackFor = ApiException.class)
//    public List<SalesReport> getSalesReport(SalesReportForm salesReportForm)throws ApiException
//    {
//        ZonedDateTime from = salesReportForm.getFrom();
//        ZonedDateTime to =salesReportForm.getTo();
//
//        if(salesReportForm.getBrand()!="" & salesReportForm.getCategory()!="" & brandService.selectByBrandCategory(salesReportForm.getBrand(), salesReportForm.getCategory())==null)
//        {
//            throw new ApiException("The Given Brand Category Combination does not exist");
//
//        }
//
//    }



}