sudo yum -y install emacs
sudo yum -y install git
sudo yum -y install java-1.7.0-openjdk-devel
cd /vagrant
if [ ! -d "/vagrant/riemann-burst" ]; then
  git clone https://github.com/Intelliplan/riemann-burst riemann-burst
  cd riemann-burst
  git checkout -b master-tweaks
  cd ..
fi
if [ ! -d "/vagrant/riemann" ]; then
  git clone https://github.com/aphyr/riemann.git riemann
  cd riemann
  git checkout -b 0.2.6-tweaks tags/0.2.6
else
  cd riemann
fi
cp /vagrant/.bashrc /home/vagrant
