#!/bin/zsh -e

./network.sh down
./network.sh up createChannel
./network.sh deployCC -ccn cocome -ccp $GITHUB_WORKSPACE -ccl java

export FABRIC_CFG_PATH=$PWD/../config/
source $GITHUB_WORKSPACE/.github/workflows/as-org1.sh

pci -C mychannel -n cocome --waitForEvent -c '{"function":"ManageStoreCRUDServiceImpl:createStore","Args":["1","Target","Weyburn","false"]}'
pci -C mychannel -n cocome --waitForEvent -c '{"function":"ManageCashDeskCRUDServiceImpl:createCashDesk","Args":["1","1","false"]}'
pci -C mychannel -n cocome --waitForEvent -c '{"function":"CoCoMESystemImpl:openStore","Args":["1"]}'
pci -C mychannel -n cocome --waitForEvent -c '{"function":"CoCoMESystemImpl:openCashDesk","Args":["1"]}'

docker stop $(docker ps -n 1 --filter 'name=dev' --format '{{.ID}}')

pci -C mychannel -n cocome --waitForEvent -c '{"function":"ProcessSaleServiceImpl:makeNewSale","Args":[]}'

if pci -C mychannel -n cocome --waitForEvent -c '{"function":"ProcessSaleServiceImpl:makeNewSale","Args":[]}'; then
  echo 'Second makeNewSale call should fail.' >&2
  exit 1
fi
