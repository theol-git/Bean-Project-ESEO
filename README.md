# Bean-Project-ESEO
# Projet Composants Logiciels
Groupe : Théo Lettermann, Sacha Bourdeau, Clément Laillé

# Guide d'installation
Vous trouverez ici toutes les étapes à effectuer pour faire fonctionner correctement le projet.

# Étape 0 - Confiugurer Éclipse
Préalablemment, assurez-vous d'avoir éclipse JEE et un workspace configuré. Il faut également configurer un "Runtime Environment" pour utiliser JBOSS et WildFLy.<br/>
Pour se faire aller sous Windows > Préférences > Server> Runtime Environments > cliquez sur "Add" > Sélectionner "WildFly 21 Runtime".<br/>
Dans la fenêtre de configuration JBoss, donnez un nom à votre Runtime Environment puis séléctionner le root directory d'un serveur WildFly 21.0.0 préalablement décompréssé.<br/>
Séléctionnez le bon jre. Nous avons utilisé pour ce projet un jre ayant pour version 14.0.2, mais le projet peut normalement fonctionner avec le JAVASE 1.8. Enfin, dans le champ configuration file, sélectionnez le fichier standalone-full.xml. (/!\ Nous avons rencontré un problème de sécurité en utilisant le jre 14.0.2, nous avons donc dû modifier notre fichier standalone-full.xml. Si une fois tout le projet configuré, lorsque vous lancez le client vous tombez sur une erreur de sécurité, veuillez ajouter cette ligne : <b>\<security enabled="false"/> à la ligne 440, après \<server name="default"> au fichier standalone-full.xml</b>.) <br/>
 
Enfin, une fois le Runtime Environment créé, il faut créer un nouveau server dans l'onglet "Servers" <br/>
Pour se faire, CLique droit > New > Server > Sélectionner WildFly 21 (laisser localhost en host name) > Next > Assigner le Runtime Environment créé > Finish<br/>
   
# Étape 1 - Configuration du serveur

Il faut désormais configurer le serveur correctement. Nous avions essayé de mettre le serveur dans un github pour pouvoir le préconfigurer et juste vous le fournir mais cela n'a pas fonctionné comme espéré. Il faut donc ajouter les utilisateurs manager/jboss et user/jboss au serveur, <b> l'utilisateur user/jboss dans le groupe "guest" </b>.<br/>
Une fois fait vous pouvez lancer le serveur. Il va falloir maintenant configurer une <b> source de données </b> et un <b> topic </b> comme vu en TPs.<br/>

<b> Source de données :</b><br/>
Lancer le serveur via Eclipse > allez sur localhost:9990 et connectez-vous avec l'utilisateur manager/jboss.<br/>
Dans l'onglet "Configuration" allez dans "Subsystems" > "Datasources & Drivers" > "Datasources" > "+" Add datasource<br/>
Sélectionnez comme configs : H2, next > Name: "MonkeysDS", JNDI : "java:jboss/datasources/MonkeysDS", next > next > JDBC Url : jdbc:h2:tcp://localhost:9092/./h2db/ejava;DB_CLOSE_DELAY=-1, url qui doit correspondre à celui indiqué lorsque vous executez le .jar dans "wildfly-21.0.0.Final\modules\system\layers\base\com\h2database\h2\main" pour lancer la datasource. Laissez également les informations de connexion à sa/sa. > Next > Test Connection, cela doit s'afficher en vert, si non pensez à bien démarrer la database h2 via le jar exécutable dans le lien précédent. > Finish<br/>

<b> Topic :</b><br/>
Toujours dans l'onglet "Configuration" du serveur, allez dans "Subsystems" > Messaging > Server > Default > Destionations > View<br/>
Allez ensuite dans "JMS Topic" > "Add" > Name: monkeysTopic, Entries : java:jboss/exported/topic/monkeys et java:jboss/exported/jms/topic/monkeys > "Save" > Reload server<br>

Le serveur est désormais prêt et configuré.

# Étape 2 - Importer tous les .war
Récupérer dans l'archive fournie tous les .war et .jar et les importer dans éclipse JEE. Il devrait y avoir :<br/>
            - 6 projets : 5 pour l'application et 1 client (3 .war et 3 .jar)<br/>
            /!\ Pour les . war utilisez l'import WAR File, pour le client utilisez l'import App Client Jar, et pour les 2 jar restants utilisez l'import EJB jar /!\<br/>
            /!\ Pour le projet MonkeyClient, une fois importé il y aura peut être encore des erreurs, il faut penser à ajouter au Build Path du projet la librairie jboss-client.jar qui se trouve dans le dossier du serveur WildFly > bin > client. Il se peut également qu'il faille ajouter la dépendance vers les autres projets "serveur" s'ils n'ont pas été ajouté avec des .jar/!\<br/>
 S'il y a des erreurs, pensez à ajouter au buildpath de chaque projet la librairie WildFly 21 Runtime. Clique droit sur le projet > Configure Build Path > Libraires > Cliquez sur "Add Library" > Sélectionner "Server Runtime" > Puis votre runtime > Finish et actualisez et CTRL + Maj + O dans chaque projet pour réorganiser les imports<br/>

Enfin, pensez à ajouter les projets au serveur, sauf le client.

# Étape 3 - Run le Client
Executez le projet MonkeysClient après avoir lancé la datasource h2 et le serveur WildFly. Normalement la fenêtre du jeu s'affiche avec les informations dans la console du serveur concernant la réception de nouveau message.
 
