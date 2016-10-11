package com.dbdemo.repository;

import com.dbdemo.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by utkarshc on 11/10/16.
 */
public class CustomerEsRepository {
    private static ObjectMapper mapper = new ObjectMapper();

    private static List<Customer> getCustomersFromEsResponse(SearchResponse response) throws IOException {
        List<Customer> returnable = new ArrayList<>();
        long n = response.getHits().getTotalHits();
        for(int i = 0; i < n; i++) {
            Customer customer = mapper.readValue(response.getHits().getAt(i).getSourceAsString(), Customer.class);
            returnable.add(customer);
        }
        return returnable;
    }

    public List<Customer> displayAll() throws IOException {
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        int from = 0, size = 500;
        List<Customer> returnable = new ArrayList<>();
        while(true) {
            SearchResponse response = ElasticSearch.search(ElasticSearch.ES_INDEX, ElasticSearch.ES_TYPE, queryBuilder, from, size);
            List<Customer> currCustomers = getCustomersFromEsResponse(response);
            returnable.addAll(currCustomers);

            if(currCustomers.size() < size)
                break;

            from += size;
        }
        return returnable;
    }

    public Customer getById(String id) throws IOException {
        QueryBuilder queryBuilder = QueryBuilders.termQuery("id", id);
        SearchResponse response = ElasticSearch.search(ElasticSearch.ES_INDEX, ElasticSearch.ES_TYPE, queryBuilder, 0, 1);
        List<Customer> currCustomers = getCustomersFromEsResponse(response);
        if(currCustomers == null || currCustomers.size() != 0)
            return null;
        return currCustomers.get(0);
    }

    public boolean createCustomer(Customer customer) throws JsonProcessingException {
        return updateCustomer(customer);
    }

    public boolean updateCustomer(Customer customer) throws JsonProcessingException {
        IndexResponse response = ElasticSearch.setDocumentById(ElasticSearch.ES_INDEX, ElasticSearch.ES_TYPE, "" + customer.getId(), "" + customer.getId(), mapper.writeValueAsString(customer));
        return true;
    }

    public boolean deleteCustomer(String id) {
        DeleteResponse response = ElasticSearch.deleteDocumentById(ElasticSearch.ES_INDEX, ElasticSearch.ES_TYPE, id, id);
        return response.isFound();
    }
}
