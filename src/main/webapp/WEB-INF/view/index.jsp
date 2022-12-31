<html>
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <title>Spring Boot Application</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
<input id="userIds" type="text" maxlength="20" placeholder="아이디" /> <button id="login">확인</button>
<div>
    <p>고정 확장자</p>
    <input type="checkbox" name="item" value="bat" id="bat">bat
    <input type="checkbox" name="item" value="cmd" id="cmd">cmd
    <input type="checkbox" name="item" value="com" id="com">com
    <input type="checkbox" name="item" value="cpl" id="cpl">cpl
    <input type="checkbox" name="item" value="exe" id="exe">exe
    <input type="checkbox" name="item" value="scr" id="scr">scr
    <input type="checkbox" name="item" value="js" id="js">js
</div>
<div>
    <p>커스텀 확장자<b id="extendCount">0</b>/200
    </p>
    <input type="text" maxlength="20" placeholder="확장자 입력" id="extendName" /> <button id="add">+추가</button> <button id="addTot">200개 추가</button>
    <div>
        <table>
            <thead>
            <tr>확장자</tr>
            </thead>
            <tbody id="extendTable">
            </tbody>
        </table>
    </div>
</div>
</body>
<script>
    let count = 0;
    var uid = '<%=session.getAttribute("userPk")%>';
    var name = '<%=session.getAttribute("userName")%>';
    $(document).ready(function () {
        if(uid!="null") {
            $("#userIds").val(name);
            $.login();
        }


        $(document).on("click", "#login", function () {
            $.login();
        });

        $("input:checkbox").on('click', function() {
            console.log($("#userIds").val());
            if($("#userIds").val()!='') {
                let data = {};
                data.extendType = $(this).val();
                $.ajax({
                    url: "/checkbox",
                    data: data,
                    method: "POST",
                    dataType: "json"
                })
                    .done(function (data) {
                    })
            } else {
                alert("아이디 입력");
                $('input:checkbox').prop('checked',false);
                return ;
            }
        });

        $(document).on("click", "#add", function () {
            if($("#userIds").val()!='') {
                let data = {};
                data.extendType = $("#extendName").val();
                $.ajax({
                    url: "/custom",
                    data: data,
                    method: "POST",
                    dataType: "json"
                })
                    .done(function (data) {
                        if (data.result == "등록 완료") {
                            $("#extendName").val('');
                            $.login();
                        } else {
                            alert(data.result);
                            return;
                        }
                    })
            }else {
                alert("아이디 입력");
                return ;
            }
        });

        $(document).on("click", "#addTot", function () {
            if($("#userIds").val()!='') {
                let data = {};
                data.extendType = $("#extendName").val();
                $.ajax({
                    url: "/custom-full",
                    data: data,
                    method: "POST",
                    dataType: "json"
                })
                    .done(function (data) {
                        if (data.result == "등록 완료") {
                            $("#extendName").val('');
                            $.login();
                        } else {
                            alert(data.result);
                            return;
                        }
                    })
            }else {
                alert("아이디 입력");
                return ;
            }
        });



        $(document).on("click", "#del", function () {
            if($("#userIds").val()!='') {
                let data = {};
                data.extendId = $(this).val();
                let thisParent = $(this).parent();
                $.ajax({
                    url: "/custom",
                    data: data,
                    method: "PUT",
                    dataType: "json"
                })
                    .done(function (data) {
                        thisParent.remove();
                        count--;
                        $("#extendCount").html(count);
                    })
            }else {
                alert("아이디 입력");
                return ;
            }
        });


    });

    $.login = function() {
        $('input:checkbox').prop('checked',false);
        $("#extendTable").empty();
        count = 0;
        let data = {};
        data.name = $("#userIds").val();
        $.ajax({
            url: "/login",
            data: data,
            method: "POST",
            dataType: "json"
        })
            .done(function (data){
                if(data.resultCode=='null'){
                    alert("공백");
                    return ;
                } else {
                    let extendType = data.result.extendTypes;
                    for(var i =0; i <extendType.length;i++) {
                        if(extendType[i].type=="N") {
                            let aa = extendType[i].name;
                            $("#" + aa).prop("checked",true);
                        } else {
                            $("#extendTable").append('<td style="border: 1px solid black;">'+extendType[i].name+'<button id=del value='+extendType[i].id+'>삭제</button></td>');
                            count++;
                        }
                    }
                }
                $("#extendCount").html(count);
            })
    }

</script>
</html>
