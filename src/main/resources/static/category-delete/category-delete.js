$(document).ready(function() {
    $('.delete-category').click(function() {
        var categoryId = $(this).data('category-id');
        if (confirm('정말로 삭제하시겠습니까?')) {
            $.ajax({
                url: '/categories/delete/' + categoryId,
                type: 'POST',
                success: function(response) {
                    // 성공적으로 삭제되었을 때 할 작업
                    alert('카테고리가 삭제되었습니다.');
                    // 새로고침 또는 화면 갱신 등
                },
                error: function(xhr, status, error) {
                    // 삭제에 실패했을 때 할 작업
                    alert('카테고리 삭제에 실패했습니다.');
                }
            });
        }
    });
});
