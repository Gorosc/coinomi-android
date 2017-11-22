package com.coinomi.core.bitwage;

import com.coinomi.core.bitwage.data.employer.payrolls.CompanyPayroll;
import com.coinomi.core.bitwage.data.user.Companies;
import com.coinomi.core.bitwage.data.employer.profile.Company;
import com.coinomi.core.bitwage.data.employer.payrolls.CompanyPayrollInfo;
import com.coinomi.core.bitwage.data.employer.workers.EmailToIdResults;
import com.coinomi.core.bitwage.data.user.Employer;
import com.coinomi.core.bitwage.data.employer.workers.Invite;
import com.coinomi.core.bitwage.data.employer.profile.LinkedAccount;
import com.coinomi.core.bitwage.data.log.InviteLog;
import com.coinomi.core.bitwage.data.worker.UserPayrollsInfo;
import com.coinomi.core.bitwage.data.user.Profile;
import com.coinomi.core.bitwage.data.employer.workers.WorkersSimple;
import com.coinomi.core.bitwage.data.UserKeyPair;
import com.coinomi.core.bitwage.data.employer.workers.WorkerSimple;
import com.coinomi.core.bitwage.data.employer.workers.Worker;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gkoro on 04-Sep-17.
 */

public class BitwageTest {

    private MockWebServer server;
    private Authentication authentication;

    private static final String USERNAME = "chr.goros@gmail.com";
    private static final String PASSWORD = "tr1n1tr0n";
    private static final String ACCESS_TOKEN = "939393";

    UserKeyPair userkeypair;

    @Before
    public void testHMACSignature() {
        String signature = Connection.getHMAC256Signature("this is an hmac test", "400025f683e43791225c");
        System.out.print(signature + "\n");
        Assert.assertEquals("22ec2da74c9b9abbe00e5681b0bed4b801298af85b385f215937e594595250f3", signature);
    }

   /* @Before
    public void setup() throws IOException {
        server = new MockWebServer();
        server.start();
        authentication = new com.coinomi.core.bitwage.Authentication();
        authentication.baseUrl = server.getUrl("/").toString();
        authentication.client.setConnectionSpecs(Collections.singletonList(ConnectionSpec.CLEARTEXT));
        authentication.setApiPublicKey("7df2a716d4004666a4ee77c62189797c");
        authentication.setSecret("d4f86342e78742b9bd334988d21ab026");
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }
    */

    @Test
    public void loginTest() {

        //server.enqueue(new MockResponse().setBody(GET_UUID_JSON));

        authentication = new Authentication();
        authentication.setApiPublicKey("7df2a716d4004666a4ee77c62189797c");
        authentication.setSecret("d4f86342e78742b9bd334988d21ab026");

        String uuid = authentication.login(USERNAME, PASSWORD);

        Assert.assertEquals("d72f22b5-cb00-4168-8f82-50ca35956f3d", uuid);

        //server.enqueue(new MockResponse().setBody(GET_SECRET_KEY));

        UserKeyPair userKeyPair = authentication.twofa(USERNAME, uuid, ACCESS_TOKEN);

        Assert.assertEquals("chr.goros@gmail.com", userKeyPair.getUsername());
        Assert.assertEquals("a1de83637ed646a6add61c6227394710", userKeyPair.getApikey());
        Assert.assertEquals("211ec2845f0640f281f0ba03605896b2", userKeyPair.getApisecret());
    }

    @Test
    public void tickersTest() throws IOException, ShapeShiftException {

        Bitwage bitwage = new Bitwage(userkeypair);
        HashMap<String, Double> tickers;
        tickers = bitwage.getTickers().getTickers();
        System.out.print(tickers.toString() + "\n");

    }

    @Test
    public void profileTest() throws JSONException, ShapeShiftException, IOException {

        userkeypair = new UserKeyPair(new JSONObject(GET_SECRET_KEY));
        Bitwage bitwage = new Bitwage(userkeypair);
        Profile profile = bitwage.getProfile();
        Assert.assertEquals("5651039470485504", profile.getUserid());
        System.out.print(profile.getFirstname() + " " + profile.getLastname() + "\n");
    }

    @Test
    public void companiesTest() throws JSONException, ShapeShiftException, IOException {

        userkeypair = new UserKeyPair(new JSONObject(GET_SECRET_KEY));
        Bitwage bitwage = new Bitwage(userkeypair);
        Companies companies = bitwage.getCompanies();
        System.out.print(companies.getCompanies().toString());

    }

    @Test
    public void bpicompanyviewTest() throws JSONException, ShapeShiftException, IOException {

        userkeypair = new UserKeyPair(new JSONObject(GET_SECRET_KEY));
        Bitwage bitwage = new Bitwage(userkeypair);
        List<Employer> employers = bitwage.bpicompanyview();
        System.out.print(employers.toString());

    }

    @Test
    public void bpicompanyeditTest() throws JSONException, ShapeShiftException, IOException {

        userkeypair = new UserKeyPair(new JSONObject(GET_SECRET_KEY));
        Bitwage bitwage = new Bitwage(userkeypair);

        Employer employer = new Employer(new JSONObject(BPIEMPLOYER));
        List<Employer> employers = bitwage.bpicompanyedit(employer);
        System.out.print(employers.toString());

    }

    @Test
    public void payrollsTest() throws JSONException, ShapeShiftException, IOException {

        server = new MockWebServer();
        server.start();

        userkeypair = new UserKeyPair(new JSONObject(GET_SECRET_KEY));
        Bitwage bitwage = new Bitwage(userkeypair);
        bitwage.baseUrl = server.getUrl("/").toString();
        bitwage.client.setConnectionSpecs(Collections.singletonList(ConnectionSpec.CLEARTEXT));

        server.enqueue(new MockResponse().setBody(PAYROLL));
        UserPayrollsInfo payrollinfo = bitwage.getPayrolls();

        System.out.print(payrollinfo.getUserpayrolls().toString());
        server.shutdown();
    }

    @Test
    public void getCompanyByidTest() throws JSONException, ShapeShiftException, IOException {

        userkeypair = new UserKeyPair(new JSONObject(GET_SECRET_KEY));
        Bitwage bitwage = new Bitwage(userkeypair);

        Company comp = bitwage.getCompanyByid(new BigInteger("5088651352473600"));
        Assert.assertEquals("Employer1", comp.getCompany_name());
        System.out.print(comp.getCompany_name());
    }

    @Test
    public void getWorkersByCompanyidTest() throws JSONException, ShapeShiftException, IOException {

        server = new MockWebServer();
        server.start();

        userkeypair = new UserKeyPair(new JSONObject(GET_SECRET_KEY));
        Bitwage bitwage = new Bitwage(userkeypair);
        bitwage.baseUrl = server.getUrl("/").toString();
        bitwage.client.setConnectionSpecs(Collections.singletonList(ConnectionSpec.CLEARTEXT));
        server.enqueue(new MockResponse().setBody(WORKERLIST));
        List<WorkerSimple> workerSimpleList = new ArrayList<>();
        WorkersSimple workers = bitwage.getWorkersByCompanyId(new BigInteger("5088651352473600"), 1);

        System.out.print(workers.getWorkerSimpleList().toString());
    }

    @Test
    public void getLinkedAccountsTest() throws JSONException, ShapeShiftException, IOException {

        //server = new MockWebServer();
        //server.start();

        userkeypair = new UserKeyPair(new JSONObject(GET_SECRET_KEY));
        Bitwage bitwage = new Bitwage(userkeypair);
        //bitwage.baseUrl = server.getUrl("/").toString();
        //bitwage.client.setConnectionSpecs(Collections.singletonList(ConnectionSpec.CLEARTEXT));
        //server.enqueue(new MockResponse().setBody(LINKEDACCOUNTS));

        List<LinkedAccount> linkedaccounts = bitwage.getLinkedAccountsByid(new BigInteger("5088651352473600"));

        System.out.print(linkedaccounts.toString());
    }

    @Test
    public void getWorkerByIdTest() throws JSONException, ShapeShiftException, IOException {

        server = new MockWebServer();
        server.start();

        userkeypair = new UserKeyPair(new JSONObject(GET_SECRET_KEY));
        Bitwage bitwage = new Bitwage(userkeypair);
        bitwage.baseUrl = server.getUrl("/").toString();
        bitwage.client.setConnectionSpecs(Collections.singletonList(ConnectionSpec.CLEARTEXT));
        server.enqueue(new MockResponse().setBody(WORKER));

        Worker worker  = bitwage.getWorkerById(new BigInteger("5088651352473600"), new BigInteger("5190345373515776"));

        System.out.print(worker.toString());
    }

    @Test
    public void inviteWorkersTest() throws JSONException, ShapeShiftException, IOException {

        server = new MockWebServer();
        server.start();

        userkeypair = new UserKeyPair(new JSONObject(GET_SECRET_KEY));
        Bitwage bitwage = new Bitwage(userkeypair);
        bitwage.baseUrl = server.getUrl("/").toString();
        bitwage.client.setConnectionSpecs(Collections.singletonList(ConnectionSpec.CLEARTEXT));
        server.enqueue(new MockResponse().setBody(INVITEWORKERS));

        List<Invite> inviteList= new ArrayList<>();
        inviteList.add(new Invite("example@example.com", "admin"));
        List<InviteLog> inviteLogList  = bitwage.inviteWorkers(new BigInteger("5088651352473600"), inviteList);

        System.out.print(inviteLogList.toString());
    }

    @Test
    public void emailtoIdTest() throws JSONException, ShapeShiftException, IOException {

        server = new MockWebServer();
        server.start();

        userkeypair = new UserKeyPair(new JSONObject(GET_SECRET_KEY));
        Bitwage bitwage = new Bitwage(userkeypair);
        bitwage.baseUrl = server.getUrl("/").toString();
        bitwage.client.setConnectionSpecs(Collections.singletonList(ConnectionSpec.CLEARTEXT));
        server.enqueue(new MockResponse().setBody(EMAILTOID));

        List<String> emails= new ArrayList<>();
        emails.add("success@success.com");
        emails.add("failure@failure.com");

        EmailToIdResults results = bitwage.emailtoId(new BigInteger("5088651352473600"), emails);

        System.out.print(results.toString());
    }

    @Test
    public void getCompanyPayrollsTest() throws JSONException, ShapeShiftException, IOException {

        server = new MockWebServer();
        server.start();

        userkeypair = new UserKeyPair(new JSONObject(GET_SECRET_KEY));
        Bitwage bitwage = new Bitwage(userkeypair);
        bitwage.baseUrl = server.getUrl("/").toString();
        bitwage.client.setConnectionSpecs(Collections.singletonList(ConnectionSpec.CLEARTEXT));
        server.enqueue(new MockResponse().setBody(COMPANY_PAYROLLS));

        CompanyPayrollInfo companyPayrolls = bitwage.getCompanyPayrolls(new BigInteger("5088651352473600"),1);

        System.out.print(companyPayrolls.toString());
    }

    @Test
    public void getCompanyPayrollByIdTest() throws JSONException, ShapeShiftException, IOException {

        server = new MockWebServer();
        server.start();

        userkeypair = new UserKeyPair(new JSONObject(GET_SECRET_KEY));
        Bitwage bitwage = new Bitwage(userkeypair);
        bitwage.baseUrl = server.getUrl("/").toString();
        bitwage.client.setConnectionSpecs(Collections.singletonList(ConnectionSpec.CLEARTEXT));
        server.enqueue(new MockResponse().setBody(COMPANY_PAYROLL));

        CompanyPayroll companyPayroll = bitwage.getCompanyPayrollById(new BigInteger("5088651352473600"),new BigInteger("4838400918028288"),1);

        System.out.print(companyPayroll.toString());
    }

    public static final String GET_UUID_JSON =
            "{" +
                    "\"username\": \"chr.goros@gmail.com\"," +
                    "\"uuid\": \"d72f22b5-cb00-4168-8f82-50ca35956f3d\"" +
                    "}";

    private static final String GET_SECRET_KEY =
            "{" +
                    "\"username\": \"chr.goros@gmail.com\"," +
                    "\"apikey\": \"a1de83637ed646a6add61c6227394710\"," +
                    "\"apisecret\": \"211ec2845f0640f281f0ba03605896b2\"" +
                    "}";

    private static final String BPIEMPLOYER =
            "{" +
                    "\"ppname\": \"payroll provider name\"," +
                    "\"created\": \"2016-09-09 05:33:08.238780\"," +
                    "\"ppwebsite\": \"https://www.bitwage.com/\"," +
                    "\"employer\": \"test employer\",\n" +
                    "\"employerwebsite\": \"https://www.bitwage.com/\"," +
                    "\"employercurrency\": \"USD\"," +
                    "\"jobrole\": \"Employee\"," +
                    "\"bpionboardid\": 6253195365934046," +
                    "\"order\": 1" +
                    "}";

    private static final String PAYROLL =
            "{" +
                    "\"payroll_fulfilled\": 5," +
                    "\"payroll_not_fulfilled\": 14," +
                    "\"total_fulfilled\": 15.02," +
                    "\"total_not_fulfilled\": 560.77," +
                    "\"total_by_currency\": {" +
                        "\"fulfilled\":{" +
                            "\"USD\": 15.02," +
                            "\"EUR\":0.0" +
                        "}," +
                        "\"not_fulfilled\":{" +
                            "\"USD\":560.77," +
                            "\"EUR\":0.0" +
                        "}" +
                    "}," +
                    "\"bpicompanyname\": \"UserPayroll Individual\"," +
                    "\"bpicompanyid\": 6471725441089536," +
                    "\"userpayrolls\": [" +
                    "{" +
                    "\"id\": 5603420492791808," +
                    "\"payroll_id\": 5040470539370496," +
                    "\"company_id\": 6471725441089536," +
                    "\"company_name\": PAtronomosBPI'," +
                    "\"payment_type\": \"ach\"," +
                    "\"created\": \"2015-06-04T20:29:59.220184\"," +
                    "\"received\": true," +
                    "\"datereceived\": \"2015-06-04T20:29:59.315871\"," +
                    "\"approved\": true," +
                    "\"dateapproved\": \"2015-06-04T20:30:15.730674\"," +
                    "\"broadcasted\": true," +
                    "\"fulfilled\": true," +
                    "\"datefulfilled\": \"2015-06-04T20:31:59.220184\"," +
                    "\"currency\":\"USD\"," +
                    "\"amount_usd\": 0.02," +
                    "\"amount_btc\": 0.02253044," +
                    "\"transaction_id\": \"e0cff7a55521f7f4b44334d74cd234dda88f596a2a2559820addb8399560fcdb\"," +
                    "\"distobj_list\": [" +
                    "{" +
                    "\"percentage\":100," +
                    "\"usercompany_wallet\": \"1CK6k5wmEqYjEbNrY25EdwNFahfdHm7p52\"," +
                    "\"payment_outlet\": \"\"," +
                    "\"paymentoutlet_orderid\": \"\"," +
                    "\"distributionobjects\": True," +
                    "\"userpayrolldistributionobjects\": True," +
                    "\"amount_usd\": 0.02," +
                    "\"amount_btc\": 0.02253044," +
                    "\"country\": \"Bitcoin\"," +
                    "\"currency\": \"BTC\"" +
                    "}]}]"+
            "}";

    private static final String WORKERLIST =  "{\"workers\": ["+
    "{\"email\": \"buspos@giants.com\",\"user_id\": 2885074604081128,\"role\": \"admin\"}," +
    "{\"email\": \"madbum@giants.com\",\"user_id\": 1230984041000002,\"role\": \"admin\"}]," +
    "  \"meta\": {\"curr_page\":1,\"next_page\":\"\",\"total_pages\":1}"+
    "}";

    public static final String LINKEDACCOUNTS = "{\n" +
            "    'linkedaccounts': [{\n" +
            "      'type': 'credit_card',\n" +
            "      'brand': 'Visa',\n" +
            "      'last4': 1234,\n" +
            "      'id': 12345678\n" +
            "    }]\n" +
            "}";

    private static final String WORKER = "{\n" +
            "  \"first_name\": \"John\",\n" +
            "  \"last_name\": \"Smith\",\n" +
            "  \"email\": \"example@example.com\",\n" +
            "  \"dob\": \"2000-01-31\",\n" +
            "  \"phone_number\": \"+1 (123) 345-9539\",\n" +
            "  \"street_address\": \"123 Main St\",\n" +
            "  \"role\": \"admin\",\n" +
            "  \"worker_id\": \"5629499534213120\",\n" +
            "  \"total_not_fulfilled\": 1000.0,\n" +
            "  \"total_fulfilled\": 0.0,\n" +
            "  \"total_by_currency\": {\n" +
            "    \"fulfilled\": {\n" +
            "        \"USD\":5.0,\n" +
            "        \"EUR\":4.0\n" +
            "    },\n" +
            "    \"not_fulfilled\": {\n" +
            "        \"USD\":1000.0,\n" +
            "        \"EUR\":0.0\n" +
            "    }\n" +
            "  },\n" +
            "  \"receive_method_entered\": false\n" +
            "}";

    private static final String INVITEWORKERS = "{\n" +
            "  \"invite_log\": [\n" +
            "    {\n" +
            "      \"email\": \"example@example.com\",\n" +
            "      \"status\": \"sent\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    private static final String EMAILTOID = "{\n" +
            "  \"success\": [\n" +
            "    {\n" +
            "      \"email\": \"success@success.com\",\n" +
            "      \"user_id\": 6685030696878080\n" +
            "    }\n" +
            "  ],\n" +
            "  \"failure\": [\n" +
            "    {\n" +
            "      \"email\": \"failure@failure.com\",\n" +
            "      \"error\": \"Worker does not exist.\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    private static final String COMPANY_PAYROLLS = "{\n" +
            "  \"company_id\": \"6403555720167424\",\n" +
            "  \"company_name\": \"Example Company\",\n" +
            "  \"payroll_not_fulfilled\": 0,\n" +
            "  \"payroll_fulfilled\": 33,\n" +
            "  \"total_fulfilled\": 0.0,\n" +
            "  \"total_not_fulfilled\": 1000.03,\n" +
            "  \"total_by_currency\": {\n" +
            "    \"fulfilled\": {\n" +
            "        \"USD\":0.0,\n" +
            "        \"EUR\":0.0\n" +
            "    },\n" +
            "    \"not_fulfilled\": {\n" +
            "        \"USD\":1000.03,\n" +
            "        \"EUR\":0.0\n" +
            "    }\n" +
            "  },\n" +
            "  \"payrolls\": [\n" +
            "    {\n" +
            "      \"amount\": 0.03,\n" +
            "      \"currency\": \"USD\",\n" +
            "      \"id\": 4838400918028288,\n" +
            "      \"time_created\": \"2015-07-16T02:07:31.937968\",\n" +
            "      \"userpayrolls\": [\n" +
            "           {\n" +
            "               \"user_email\": \"test@example.com\", \n" +
            "               \"user_id\": 5677071464398848, \n" +
            "               \"userpayroll_id\": 6523493654986752\n" +
            "           }\n" +
            "       ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"amount\": 1.00,\n" +
            "      \"currency\": \"USD\",\n" +
            "      \"id\": 4234242238028288,\n" +
            "      \"time_created\": \"2015-07-16T02:08:36.923968\",\n" +
            "      \"userpayrolls\": [\n" +
            "           {\n" +
            "               \"user_email\": \"test2@example.com\", \n" +
            "               \"user_id\": 5677071364398848, \n" +
            "               \"userpayroll_id\": 6523423654986752\n" +
            "           }\n" +
            "       ]\n" +
            "    }\n" +
            "  ],\n" +
            "  \"meta\":{\n" +
            "    \"curr_page\":1,\n" +
            "    \"next_page\":\"\",\n" +
            "    \"total_pages\":1\n" +
            "  }\n" +
            "}";

    private static final String COMPANY_PAYROLL = "{\n" +
            "  \"time_created\": \"2015-06-30T00:48:01.265676\",\n" +
            "  \"date_received\": \"2015-06-20\",\n" +
            "  \"date_processed\": \"2015-06-21\",\n" +
            "  \"date_fulfilled\": \"2015-06-22\",\n" +
            "  \"total_amount\": 1.03,\n" +
            "  \"currency\": \"USD\",\n" +
            "  \"userpayrolls\": [\n" +
            "    {\n" +
            "      \"amount\": 0.03,\n" +
            "      \"currency\": \"USD\",\n" +
            "      \"user_id\": 4838400918028288,\n" +
            "      \"email\": \"test@example.com\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"amount\": 1.00,\n" +
            "      \"currency\": \"USD\",\n" +
            "      \"user_id\": 4234242238028288,\n" +
            "      \"email\": \"test2@example.com\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"payment_type\": \"ach\",\n" +
            "  \"payment_details\": {\n" +
            "    \"printOnCheck\": \"Individual\",\n" +
            "    \"address\": \"123 Main St.\",\n" +
            "    \"checkNum\": \"3333333333\",\n" +
            "    \"city\": \"Los Angeles\",\n" +
            "    \"memo\": \"A+\",\n" +
            "    \"phone\": \"+1 (123) 633-0123\",\n" +
            "    \"bankAccNum\": \"XXXXXX2222\",\n" +
            "    \"bankRoute\": \"1111111111\",\n" +
            "    \"state\": \"CA\",\n" +
            "    \"email\": \"example@example.com\",\n" +
            "    \"first_name\": \"John\",\n" +
            "    \"last_name\": \"Smith\",\n" +
            "    \"zipcode\": \"95070\"\n" +
            "  },\n" +
            "  \"meta\": {\n" +
            "    \"curr_page\":1,\n" +
            "    \"next_page\":\"\",\n" +
            "    \"total_pages\":1\n" +
            "  }\n" +
            "}";
}