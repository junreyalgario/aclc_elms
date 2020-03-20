<?php

	if (isset($_GET['message'])) {
		if (send_message($_GET['contact_no'], $_GET['message']) == 0) {
			echo 1;
		} else {
			echo 0;
		}
	}
	
	function send_message($number = "", $message = ""){
		$url = 'https://www.itexmo.com/php_api/api.php';
		$apicode = 'TR-JUNJU676516_6A6FG';

		$post_body = array('1' => $number, '2' => $message, '3' => $apicode);
		$param = array(
		    'http' => array(
		        'header'  => "Content-type: application/x-www-form-urlencoded\r\n",
		        'method'  => 'POST',
		        'content' => http_build_query($post_body),
		    ),
		);

		$context  = stream_context_create($param);
		return file_get_contents($url, false, $context);
	}

?>