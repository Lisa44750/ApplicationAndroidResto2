<?php
try {
// Connexion à la base de données MySql
$db="resto2";
$dbhost="localhost";
$dbport=3306;
$dbuser="root";
$dbpasswd="joliverie";
 
$connexion = new PDO('mysql:host='.$dbhost.';port='.$dbport.';dbname='.$db.'', $dbuser, $dbpasswd);
$connexion->exec("SET CHARACTER SET utf8");
                        
        $reponse=$connexion->prepare("SELECT nomR, numAdrR, voieAdrR, cpR, villeR, descR, horairesR FROM resto;");
        $reponse->execute();
        $datas = array();
         
        while($res=$reponse->fetch(PDO::FETCH_ASSOC)) {
            $datas['restos'][]=$res;
			
        }
             
      echo json_encode($datas);
	   
}
catch (Exception $e) {
die('Erreur : ' . $e->getMessage());
}

?>
