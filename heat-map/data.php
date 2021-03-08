<?php 
    $eye_tracking_data = [[38,20,100], [50,20,100], [150,50,100]];
?>

<script>
    var data = <?php echo json_encode($eye_tracking_data); ?>;
</script>