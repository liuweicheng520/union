//add
$('#btn-add').click(function (e) {

    var name = document.getElementById("ad-name").value;
    var title = document.getElementById("ad-title").value;
    var createTime = document.getElementById("ad-startTime").value;
    var endTime = document.getElementById("ad-endTime").value;
    var booklist = {
        'inquiryName': name, 'topicName': title,
        'createTime': createTime, 'endTime': endTime,
    };
    var JSONdata = JSON.stringify(booklist);
    $.ajax({
        type: "post",
        url: "/user/inquiry/add.do",
        data: JSONdata,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function (result) {
            if (result.code == 200) {
                alert(result.data);
            } else {
                alert("添加失败");
            }
        }
    });
})
;

$('#btn-delete').click(function (e) {
    if (!checkadd.every(function (value) {
        return value == true
    })) {
        e.preventDefault();
        for (key in checkdel) {
            if (!checkdel[key]) {
                $('#delete').find('input').eq(key).parent().parent().removeClass('has-success').addClass('has-error')
            }
        }
    } else {
        var ISBN = document.getElementById("dl-isbn").value;
        var booklist = {
            'isbn': ISBN
        };
        var JSONdata = JSON.stringify(booklist);
        $.ajax({
            type: "post",
            url: "/book/admin/delete",
            data: JSONdata,
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                if (result['success']) {
                    alert("删除成功");
                } else {
                    alert("删除失败");
                }
            }
        });
    }
});
