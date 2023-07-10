/**
 * 
 */
 
 // 친구 검색 기능

$(function() {
    $('#inputSearch').autocomplete({
        source : function(reuqest, response) {
            $.ajax({
                type : 'post',
                url: '/api/user/list',
                dataType : 'json',
                data : {value : reuqest.term},
                success : function(data) {
                    // 서버에서 json 데이터 response 후 목록 추가
                    response(
                        $.map(data, function(item) {
							console.log("데이터" +item);
                            return {
                                label : item.id,
                                value : item.username,
                                test : item + 'test'
                            }
                        })
                    );
                }
            });
        },
        select : function(event, ui) {
            console.log(ui);
            console.log(ui.item.label);
            console.log(ui.item.value);
            console.log(ui.item.test);
        },
        focus : function(event, ui) {
            return false;
        },
        minLength : 1,
        classes : {
            'ui-autocomplete': 'highlight'
        },
        delay : 500,
        position : { my : 'right top', at : 'right bottom' },
        close : function(event) {
            console.log(event);
        }
        
    }).autocomplete('instance')._renderItem = function(ul, item) { // UI 변경 부
        return $('<li>') //기본 tag가 li
        .append(`<div onclick="location.href='/user/${item.label}'">` + item.value + `</div>`  ) // 원하는 모양의 HTML 만들면 됨
        .appendTo(ul);
    };
});

