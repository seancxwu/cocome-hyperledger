#!/bin/bash

oneTimeSetUp() {
	export FABRIC_CFG_PATH=$PWD/../config/

	if [[ -z "$GITHUB_WORKSPACE" ]]; then
		GITHUB_WORKSPACE=~/cocome-hyperledger/
	fi

	export CORE_PEER_TLS_ENABLED=true
	export CORE_PEER_LOCALMSPID="Org1MSP"
	export CORE_PEER_TLS_ROOTCERT_FILE=~/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt
	export CORE_PEER_MSPCONFIGPATH=~/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp
	export CORE_PEER_ADDRESS=localhost:7051

	rm -f $GITHUB_WORKSPACE/cocome.tar.gz
	rm -rf "$GITHUB_WORKSPACE/build/install/"
	pushd "$GITHUB_WORKSPACE"
	./gradlew installDist
	popd
	peer lifecycle chaincode package $GITHUB_WORKSPACE/cocome.tar.gz --path $GITHUB_WORKSPACE/build/install/cocome --lang java --label cocome_1.0

}

setUp() {
	(cd addOrg3; ./addOrg3.sh down)
	./network.sh down
	./network.sh up createChannel
	(cd addOrg3; ./addOrg3.sh up)

	# start a subshell due to export variables.
	(
		# approve as org1
		export CORE_PEER_TLS_ENABLED=true
		export CORE_PEER_LOCALMSPID="Org1MSP"
		export CORE_PEER_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt
		export CORE_PEER_MSPCONFIGPATH=${PWD}/organizations/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp
		export CORE_PEER_ADDRESS=localhost:7051
		# The package ID is the combination of the chaincode label and a hash of the chaincode binaries. Every peer will generate the same package ID.
		packgeId=$(peer lifecycle chaincode install $GITHUB_WORKSPACE/cocome.tar.gz 2>&1 | grep -o -P '(?<=identifier:\s).+:[\da-f]+$')

		peer lifecycle chaincode approveformyorg -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --channelID mychannel --name cocome --version 1.0 --package-id $packgeId --sequence 1 --tls --cafile ${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem

		# approve as org2
		export CORE_PEER_LOCALMSPID="Org2MSP"
		export CORE_PEER_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt
		export CORE_PEER_MSPCONFIGPATH=${PWD}/organizations/peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp
		export CORE_PEER_ADDRESS=localhost:9051
		peer lifecycle chaincode install $GITHUB_WORKSPACE/cocome.tar.gz

		peer lifecycle chaincode approveformyorg -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --channelID mychannel --name cocome --version 1.0 --package-id $packgeId --sequence 1 --tls --cafile ${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem

		# approve as org3
		export CORE_PEER_LOCALMSPID="Org3MSP"
		export CORE_PEER_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/org3.example.com/peers/peer0.org3.example.com/tls/ca.crt
		export CORE_PEER_MSPCONFIGPATH=${PWD}/organizations/peerOrganizations/org3.example.com/users/Admin@org3.example.com/msp
		export CORE_PEER_ADDRESS=localhost:11051
		peer lifecycle chaincode install $GITHUB_WORKSPACE/cocome.tar.gz

		peer lifecycle chaincode approveformyorg -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --channelID mychannel --name cocome --version 1.0 --package-id $packgeId --sequence 1 --tls --cafile ${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem

		peer lifecycle chaincode commit -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --channelID mychannel --name cocome --version 1.0 --sequence 1 --tls --cafile ${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem \
			--peerAddresses localhost:7051 --tlsRootCertFiles ${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt \
			--peerAddresses localhost:9051 --tlsRootCertFiles ${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt \
			--peerAddresses localhost:11051 --tlsRootCertFiles ${PWD}/organizations/peerOrganizations/org3.example.com/peers/peer0.org3.example.com/tls/ca.crt

	)
}

getBlockInfo() {
	peer channel fetch newest mychannel.block -c mychannel -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --tls --cafile ${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem
	configtxlator proto_decode --type common.Block --input mychannel.block
}

function pci() {
	peer chaincode invoke -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --tls --cafile ${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem \
		-C mychannel -n cocome --waitForEvent \
		--peerAddresses localhost:7051 --tlsRootCertFiles ${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt \
		--peerAddresses localhost:9051 --tlsRootCertFiles ${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt \
		--peerAddresses localhost:11051 --tlsRootCertFiles ${PWD}/organizations/peerOrganizations/org3.example.com/peers/peer0.org3.example.com/tls/ca.crt \
		"$@"
}

function pci23() {
	# run command on org 2 and org 3.
	peer chaincode invoke -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --tls --cafile ${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem \
		-C mychannel -n cocome --waitForEvent \
		--peerAddresses localhost:9051 --tlsRootCertFiles ${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt \
		--peerAddresses localhost:11051 --tlsRootCertFiles ${PWD}/organizations/peerOrganizations/org3.example.com/peers/peer0.org3.example.com/tls/ca.crt \
		"$@"
}

testMakeNewSale() {
	pci -c '{"function":"ManageStoreCRUDServiceImpl:createStore","Args":["1","Target","Weyburn","false"]}' || fail || return
	pci -c '{"function":"ManageStoreCRUDServiceImpl:createStore","Args":["2","Whole Foods","Gayley","false"]}' || fail || return

#	pci -c '{"function":"ManageCashDeskCRUDServiceImpl:createCashDesk","Args":["1","1","false"]}' || fail || return
	pci -c '{"function":"CoCoMESystemImpl:openStore","Args":["1"]}' || fail || return

	docker network disconnect fabric_test "$(docker ps --filter 'name=dev-peer0.org1' --format '{{.ID}}')"
	pci23 -c '{"function":"CoCoMESystemImpl:openStore","Args":["2"]}' || fail || return
	docker network connect fabric_test "$(docker ps --filter 'name=dev-peer0.org1' --format '{{.ID}}')"

	pci -c '{"function":"CoCoMESystemImpl:closeCurrentStore","Args":[]}' || fail || return
}

source shunit2
