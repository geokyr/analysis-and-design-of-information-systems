echo -e "\n" | sudo tee -a /etc/hosts
echo -e "192.168.1.1\tmaster" | sudo tee -a /etc/hosts
echo -e "192.168.1.2\tslave1" | sudo tee -a /etc/hosts
echo -e "192.168.1.3\tslave2" | sudo tee -a /etc/hosts
