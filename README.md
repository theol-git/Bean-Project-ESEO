# Bean-Project-ESEO
# Projet Composants Logiciels
Groupe : Théo Lettermann, Sacha Bourdeau, Clément Laillé

# Guide d'installation
Vous trouverez ici toutes les étapes à effectuer pour faire fonctionner correctement le projet.

# Étape 0 - Confiugurer Éclipse
Préalablemment, assurez-vous d'avoir éclipse JEE et un workspace configuré. Il faut également configurer un "Runtime Environment" pour utiliser JBOSS et WildFLy.<br/>
Pour se faire aller sous Windows > Préférences > Server> Runtime Environments > cliquez sur "Add" > Sélectionner "WildFly 21 Runtime".<br/>
Dans la fenêtre de configuration JBoss, donnez un nom à votre Runtime Environment puis séléctionner le root directory d'un serveur WildFly 21.0.0 préalablement décompréssé.\n
Séléctionnez le bon jre. Nous avons utilisé pour ce projet un jre ayant pour version 14.0.2, mais le projet peut normalement fonctionner avec le JAVASE 1.8. Enfin, dans le champ configuration file, sélectionnez le fichier standalone-full.xml. (/!\ Nous avons rencontré un problème de sécurité en utilisant notre jre 14.0.2, nous avons donc dû modifier notre fichier standalone-full.xml dans notre cas. Si une fois tout le projet configurer lorsque vous lancerez le client vous tombez sur une erreur de sécurité, veuillez ajouter cette ligne : <b><security enabled="false"/> à la ligne 440, après <server name="default"> au fichier standalone-full.xml</b>.) <br/>
  
# Étape 1 - Importer tous les .war
  Récupérer dans l'archive fournie tous les .war et .jar et les importer dans éclipse JEE. Il devrait y avoir :
            - 6 projets : 5 pour l'application et 1 client (3 .war et 3 .jar)
 
