

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
