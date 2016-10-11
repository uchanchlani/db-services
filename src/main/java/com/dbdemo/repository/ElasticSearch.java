package com.dbdemo.repository;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * Created by utkarshc on 11/10/16.
 */
public class ElasticSearch {
    private static TransportClient esClient = null;
    public static String ES_CLUSTERNAME = "elasticsearch";
    public static String ES_HOSTS = "127.0.0.1:9300";
    public static int ES_TRANSPORT_PING_TIMEOUT_IN_SECS = 10;
    public static String ES_INDEX = "v0.0.1-snapshot";
    public static String ES_TYPE = "customer";

    static {
        try {
            ElasticSearch.connect();
        }
        catch(Exception e) {
            System.out.println("Error instantiating ElasticSearchManager " +e.getMessage());
            System.exit(1);
        }
    }


    public static boolean connect() throws Exception {


        Settings settings = ImmutableSettings.settingsBuilder()
                .put("client.transport.ping_timeout", ES_TRANSPORT_PING_TIMEOUT_IN_SECS+"s")
                .put("cluster.name", ES_CLUSTERNAME).build();
        esClient = new TransportClient(settings);

        String[] hs = ES_HOSTS.split(",");
        for(String h : hs) {
            String[] hp = h.split(":");
            esClient = esClient.addTransportAddress(new InetSocketTransportAddress(hp[0], Integer.parseInt(hp[1])));
        }
        IndicesExistsResponse elemIndexExists =  esClient.admin().indices().prepareExists(ES_INDEX).execute().actionGet();
        if(!elemIndexExists.isExists()){
            throw new Exception("Exit no ES index " + ES_INDEX);
        }

        return true;
    }

    public static SearchResponse search(String idx, String type, QueryBuilder qb, int from, int size) {
        return esClient.prepareSearch(idx)
                .setTypes(type)
                .setQuery(qb)
                .setFrom(from)
                .setSize(size)
                .execute().actionGet();
    }

    public static GetResponse getDocumentById(String index, String type, String docId, String routing) {
        GetRequestBuilder builder = esClient.prepareGet(index, type, docId);
        if(routing != null && !routing.equalsIgnoreCase(""))
            builder = builder.setRouting(routing);
        return builder.get();
    }

    public static IndexResponse setDocumentById(String index, String type, String docId, String routing, String json) {
        IndexRequestBuilder request = esClient.prepareIndex(index, type)
                .setId(docId)
                .setSource(json);

        if(routing != null && !routing.equalsIgnoreCase(""))
            request.setRouting(routing);
        return request.get();
    }

    public static DeleteResponse deleteDocumentById(String index, String type, String docId, String routing) {
        DeleteRequestBuilder builder = esClient.prepareDelete(index, type, docId);

        if(routing != null && !routing.equalsIgnoreCase(""))
            builder.setRouting(routing);

        return builder.get();
    }
}
