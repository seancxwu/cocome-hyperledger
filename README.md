

Deploy

```bash
cd ~/fabric-samples/test-network
./network.sh deployCC -ccn cocome -ccp ~/cocome -ccl java
```

Run the following to check the chaincode metadata. Verify if the expected contracts are there.
```
peer chaincode query -C mychannel -n cocome -c '{"Args":["org.hyperledger.fabric:GetMetadata"]}'
```

To pretty-format the json output, you may pipe the result to `jq`.


Test transactions:

```
$ peer chaincode invoke --invokeOptions -C mychannel -n cocome -c '{"function":"ManageStoreCRUDServiceImpl:setB","Args":["true"]}'
2021-08-27 18:53:31.179 CST [chaincodeCmd] chaincodeInvokeOrQuery -> INFO 001 Chaincode invoke successful. result: status:200
$ peer chaincode query -C mychannel -n cocome -c '{"Args":["ManageStoreCRUDServiceImpl:getB"]}'
true
$ peer chaincode invoke --invokeOptions -C mychannel -n cocome -c '{"function":"ManageStoreCRUDServiceImpl:setB","Args":["false"]}'
2021-08-27 18:54:35.529 CST [chaincodeCmd] chaincodeInvokeOrQuery -> INFO 001 Chaincode invoke successful. result: status:200
$ peer chaincode query -C mychannel -n cocome -c '{"Args":["ManageStoreCRUDServiceImpl:getB"]}'
false
```

Test make sales use case:

```
alias pci="peer chaincode invoke -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --tls --cafile ${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C mychannel --peerAddresses localhost:7051 --tlsRootCertFiles ${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt --peerAddresses localhost:9051 --tlsRootCertFiles ${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt"

pci -C mychannel -n cocome --waitForEvent -c '{"function":"ManageStoreCRUDServiceImpl:createStore","Args":["1","Target","Weyburn","false"]}'
pci -C mychannel -n cocome --waitForEvent -c '{"function":"ManageCashDeskCRUDServiceImpl:createCashDesk","Args":["1","1","false"]}'
pci -C mychannel -n cocome --waitForEvent -c '{"function":"CoCoMESystemImpl:openStore","Args":["1"]}'
pci -C mychannel -n cocome --waitForEvent -c '{"function":"CoCoMESystemImpl:openCashDesk","Args":["1"]}'
pci -C mychannel -n cocome --waitForEvent -c '{"function":"ProcessSaleServiceImpl:makeNewSale","Args":[]}'
```
