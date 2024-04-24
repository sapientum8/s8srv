package com.sapientum8

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import org.apache.logging.log4j.LogManager
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest
import kotlin.system.exitProcess

class Handler : RequestHandler<Map<String, Any>, ApiGatewayResponse> {
    override fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {
        LOG.info("received: " + input.keys.toString())

        val client: DynamoDbClient = DynamoDbClient.builder()
            .region(Region.US_EAST_1)
            .build()

        getDynamoDBItem(client, "Books", "book", "book101");

        client.close();

        return ApiGatewayResponse.build {
            statusCode = 200
            objectBody = HelloResponse("Go Serverless v2.3! Your Kotlin function executed successfully!!", input)
            headers = mapOf("X-Powered-By" to "AWS Lambda & serverless")
        }
    }

    companion object {
        private val LOG = LogManager.getLogger(Handler::class.java)
    }

    fun getDynamoDBItem(ddb: DynamoDbClient, tableName: String?, key: String, keyVal: String?) {
        val keyToGet = HashMap<String, AttributeValue>()
        keyToGet[key] = AttributeValue.builder().s(keyVal).build()
        val request = GetItemRequest.builder().key(keyToGet).tableName(tableName).build()
        try {
            // If there is no matching item, GetItem does not return any data.
            val returnedItem = ddb.getItem(request).item()
            if (returnedItem.isEmpty())
                System.out.format("No item found with the key %s!\n", key)
            else {
                val keys: Set<String> = returnedItem.keys
                println("Amazon DynamoDB table attributes: \n")
                for (key1 in keys) {
                    System.out.format("%s: %s\n", key1, returnedItem[key1].toString())
                }
            }
        } catch (e: DynamoDbException) {
            System.err.println(e.message)
            exitProcess(1)
        }
    }
}
