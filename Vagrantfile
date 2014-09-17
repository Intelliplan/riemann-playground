# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|
  config.vm.box = 'centos-6.5-x64'
  config.vm.synced_folder '.', '/vagrant', mount_options: ["dmode=775","fmode=777"]
  config.vm.network :forwarded_port, guest: 5557, host: 5557
  config.vm.provision "shell", path: "provision.sh"
end

