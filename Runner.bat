chcp 65001

cd E:\Projects\deliveryService
dir /s/b/o *.java >sourse
javac -d target -cp .\lib\gson-2.8.6.jar @sourse
del sourse
java -cp ./target;./lib/gson-2.8.6.jar by.home.serviseDelivery.Starter

pause
