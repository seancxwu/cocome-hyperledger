

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
