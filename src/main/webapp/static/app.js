function toJSON($form)
{
    var serialized = $form.serializedArray();
    console.log(serialized);
    var s = '';
    var data = {};
    for(s in serialized)
    {
        data[serialized[s]['name']]=serialized[s]['value'];
    }
    var json = JSON.stringify(data);
    return json;
}

function handleAjaxError(response)
{
    var response = JSON.parse(response.responseText);
    alert(response.message);
}

function realFileData(file,callback)
{
    var config = {
        header : true,
        delimiter : "\t",
        skipEmptyLines : "greedy",
        complete : function(results)
        {
            callback(results);
        }
    }
    Papa.parse(file,config);
}

function loadBrand()
{
    //INSTANTIATE A NEW XHR OBJECT
    const xhr = new XMLHttpRequest();
    xhr.open("GET","http://localhost:9001/PointOfSale/brands",true);

    xhr.onload = function()
    {
        if(this.status === 200)
        {
            //CHANGING STRING DATA TO JSON OBJECT
            obj = JSON.parse(this.responseText);

            //GETTING THE UI ELEMENT
            let body = document.getElementById("brandTbody");
            str="";
            for(var i=0;i<obj.length;i++)
            {
                str+=`<tr>
                        <th scope="row">$(i+1)</th>
                        <td>${obj[i]['brand']}</td>
                        <td>${obj[i]['category']}</td>
                               
                        <td><button type='button' class='btn btn-primary' onclick=editBrandModal(${obj[i]['id']},"${obj[i]['brand']}","${obj[i]['category']}")>Edit</button></td>
                        </tr>`;
            }
            body.innerHTML = str;
        }
        else
        {
            console.log("Cannot fetch brand details");
        }
    }
    xhr.send();
}

function deleteBrandModal(id)
{
    document.getElementById("deleteModalBtn").setAttribute("onClick",`deleteBrand(${id})`);
    $("#confirmModalBody").html("<p>Do u wanna Delete?</p>");
    $("#brandDelete").modal('show');
}
function deleteBrand(id)
{
    const xhr = new XMLHttpRequest();
    $.ajax({
        type:"DELETE",
        url:`http://localhost:9001/PointOfSale/delete${id}`,
        success:function(result,status,error)
        {
            loadBrand()
            console.log(id," is Deleted")
        },
        error: function(xhr,status,error)
        {
            console.log(status,error,xhr)
            document.getElementById("deleteModalBtn").setAttribute("onClick","");
            $("#confirmModalBody").html(xhr['responseJSON']['description'])
            $("#brandDelete").modal('show');
        }
    });

$("brandDelete").modal('hide');
}

function changeBrand()
{
    let brand = document.getElementById("brandInput").value;
    console.log("brand change",brand)
    document.getElementById("brandInput").setAttribute("value",brand);
}

function changeCategory()
{
    let category=document.getElementById("categoryInput").value;
    console.log("brand change",category)
    document.getElementById("categoryInput").setAttribute("value",category);
}

function editBrandModal(id,brand,category)
{
    setBrandModal();
    console.log(id,brand,category)
    $("#modalTitle").html("Update Brand")
    document.getElementById("brandInput").setAttribute("value",brand);
    document.getElementById("categoryInput").setAttribute("value",category);
    document.getElementById("EditModalBtn").setAttribute("onclick",`updateBrandForm(${id},'${brand}','${category}')`);
    $("#EditModalBtn").text('Update');
    $("#modalFormErrorDiv").html("");
    $("brandEdit").modal('show');
}

const form = document.getElementById('editBrandForm');

function updateBrandForm(id,brand,category)
{
    let brandInput= document.getElementById("brandInput").value.trim();
    let categoryInput = document.getElementById("categoryInput").value.trim();
    console.log(brand,brandInput,category,categoryInput)

    if(brandInput!==""||categoryInput!=="")
    {
        $.ajax({
            contentType:'application/json',
            data: JSON.stringify({
                "brand":brandInput,
                "category": categoryInput,
                "id":id
            }),
            dataType:'json',
            success:function(data)
            {
                console.log("Updated")
                $("#brandEdit").modal('hide');
                loadBrand();
            },
            error: function(e)
            {
                console.log(e)
                $("#modalFormErrorDiv").html(`*${e['responseJSON']['description']}`);
            },
            processData: false,
            type:'PUT',
            url: `http://localhost:9001/pos/brands/${id}`
        });
    }

    event.preventDefault();
}

function addBrandModal()
{
    setBrandModal();
    $("#modalTitle").html("Add Brand")
    document.getElementById("brandInput").setAttribute("value","");
    document.getElementById("categoryInput").setAttribute("value","");
    document.getElementById("EditModalBtn").setAttribute("onclick",`addBrandForm()`);
    $("#EditModalBtn").text('Add');
    $("#modalFormErrorDiv").html("");
    $("brandEdit").modal('show');
}

function addBrandForm()
{
    let brandInput = document.getElementById("brandInput").value.trim();
    let categoryInput = document.getElementById("categoryInput").value.trim();
    console.log(brandInput,categoryInput)

    if(brandInput==""||categoryInput=="")
    {
        $("#modalFormErrorDiv").html("*Invalid input");
    }
    else
    {
        $.ajax({
            contentType:'application/json',
            data: JSON.stringify({
                "brand":brandInput,
                "category":categoryInput
            }),
            dataType:'json',
            success:function(data)
            {
                console.log("Updated");
                $("brandEdit").modal('hide');
                $("#modalFormErrorDiv").html("");
                loadBrand();
            },
            error:function(e)
            {
                console.log(e)
                $("#modalFormErrorDiv").html(`*${e['responseJSON']['description']}`);
            },
            processData:false,
            type:'POST',
            url:'http://localhost:9001/PointOfSale/brands/'
        });
        event.preventDefault();
    }
}

function bulkAddBrandModal()
{
    $("uploadModalFormErrorDiv").html("");
    $("#bulkUpload").modal('show');
}

function bulkAddBrand()
{
    let f = document.getElementById('formFile');
    if(f.files && f.files[0])
    {
        console.log(f.files[0]['name'],f.files[0])
        var reader = new FileReader();
        reader.addEventListener('load',function(e)
        {
            let tsvData = e.target.result;
            let csvData = e.target.result;
            bulkAddUtil(csvData);
        });
        reader.readAsBinaryString(f.files[0]);
    }
    event.preventDefault();
}

function bulkAddUtil(data)
{
    let parsedata=[];
    data= data.split('\r').join('');
    let newLinebrk = data.split("\n");
    console.log(newLinebrk);
    head = newLinebrk[0].split("\t");
    console.log(head)
    if(head.length!=2)
    {
        $("#uploadModalFormErrorDiv").html("*Column names should be brand and category only");
    }
    else if(!((head[0].toLowerCase()==="brand" && head[1].toLowerCase()=="category")||(head[0].toLowerCase==="category" && head[1].toLowerCase()==="brand")))
    {
        $("#uploadModalFormErrorDiv").html("*Column Names should be brand and category only");
    }
    else if(newLinebrk.length > 1000)
    {
        $("#uploadModalFormErrorDiv").html("*Please upload a file that is less than 1000 rows<br>LIMIT : 1000 rows");
    }
    else
    {

        fileError = ""
        for(let i =1;i<newLinebrk.length;i++)
        {
            row = newLinebrk[i].split("\t");
            console.log(row);
            if(row.length ==1 && row[0]=="")
                continue;
            if(row.length !=2)
            {
                filrError+= "Syntax Error in row "+ i + "<br>";
                continue;
            }
            let jsonData ={};
            jsonData[head[0].toLowerCase()]=row[0].toLowerCase();
            jsonData[head[1].toLowerCase()]=row[1].toLowerCase();
            parsedata.push(jsonData);
        }
        console.table(parsedata);
        if(fileError !="")
        {
            console.log(fileError);
            $("#confirmModalBody").html(fileError);
            $("#bulkUpload").modal('hide');
            document.getElementById('brandDelete').setAttribute("data-dismiss","");
            $('#brandDelete').modal('show');
        }
        else
        {
            const xhr = new XMLHttpRequest();
            $.ajax({
                contentType : 'application/json',
                data : JSON.stringify(parsedata),
                dataType  : JSON,
                success : function(data)
                {
                    console.log("Inserted");
                    $("bulkUpload").modal('hide');
                    $("modalFormErrorDiv").html("");
                    loadBrand();
                },
                error : function(e)
                {
                    console.log(e)
                    console.log(fileError);
                    $("confirmModalBody").html(e['responseJSON']['description'].replaceAll("\n","<br>"));
                    $("#bulkUpload").modal("hide");
                    document.getElementById("brandDelete").setAttribute("data-dismiss","");
                    $("#brandDelete").modal("show");
                },
                processData : false,
                type : "POST",
                url : "http://localhost:9001/PointofSale/brands/upload"
            });
        }

    }
}

function setBrandModal() {
    console.log("modal is set")
    $('#brandEdit').html(`
                          <div class="modal-dialog" role="document">
                            <div class="modal-content">
                              <div class="modal-header">
                                <h5 class="modal-title" id="modalTitle"></h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                  <span aria-hidden="true">&times;</span>
                                </button>
                              </div>
                              <div class="modal-body">
                                <form id="editBrandForm" >
                                  <div id="modalFormDataDiv">
                                    <div class="form-group">
                                      <label for="brandInput">Brand</label>
                                      <input type="text" class="form-control" id="brandInput" name="brand" aria-describedby="text" placeholder="Enter Brand" autocomplete="off" minlength="1" onchange="changeBrand()">
                                    </div>
                                    <div class="form-group">
                                      <label for="categoryInput">Category</label>
                                      <input type="text" class="form-control" id="categoryInput"  name="category" aria-describedby="text" placeholder="Enter Category" autocomplete="off" minlength="1" onchange="changeCategory()">
                                    </div>
                                    <div id="modalFormErrorDiv" style="color:red; font-size: 14px;">
                                    <!--error-->
                                    </div>
                                  </div>
                                  <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                    <button type="button" class="btn btn-primary" id="EditModalBtn" ></button>
                                  </div>
                                </form>
                              </div>
  
                            </div>
                          </div>
  
  `)
  }