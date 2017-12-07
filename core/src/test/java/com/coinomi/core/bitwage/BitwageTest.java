package com.coinomi.core.bitwage;

import com.coinomi.core.bitwage.data.employer.invoices.CompanyInvoices;
import com.coinomi.core.bitwage.data.employer.invoices.Invoice;
import com.coinomi.core.bitwage.data.employer.invoices.InvoiceApproval;
import com.coinomi.core.bitwage.data.employer.payrolls.CompanyPaymentMethod;
import com.coinomi.core.bitwage.data.employer.payrolls.CompanyPayroll;
import com.coinomi.core.bitwage.data.employer.payrolls.PayrollCreation;
import com.coinomi.core.bitwage.data.employer.payrolls.WorkerPayrolls;
import com.coinomi.core.bitwage.data.user.Companies;
import com.coinomi.core.bitwage.data.employer.profile.Company;
import com.coinomi.core.bitwage.data.employer.payrolls.CompanyPayrollInfo;
import com.coinomi.core.bitwage.data.employer.workers.EmailToIdResults;
import com.coinomi.core.bitwage.data.user.Employer;
import com.coinomi.core.bitwage.data.employer.workers.Invite;
import com.coinomi.core.bitwage.data.employer.profile.LinkedAccount;
import com.coinomi.core.bitwage.data.log.DeletePayrollLog;
import com.coinomi.core.bitwage.data.log.InviteLog;
import com.coinomi.core.bitwage.data.worker.UserPayrollsInfo;
import com.coinomi.core.bitwage.data.user.Profile;
import com.coinomi.core.bitwage.data.employer.workers.WorkersSimple;
import com.coinomi.core.bitwage.data.PaymentMethod;
import com.coinomi.core.bitwage.data.UserKeyPair;
import com.coinomi.core.bitwage.data.employer.workers.WorkerSimple;
import com.coinomi.core.bitwage.data.employer.workers.Worker;
import com.coinomi.core.exchange.shapeshift.data.ShapeShiftException;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gkoro on 04-Sep-17.
 */

public class BitwageTest {

    private MockWebServer server;
    private Authentication authentication;
    private Bitwage bitwage;

    private static final String USERNAME = "joeshoe@gmail.com";
    private static final String PASSWORD = "asjdfioas";
    private static final String ACCESS_TOKEN = "939393";

    UserKeyPair userkeypair;

    @Before
    public void testHMACSignature() {
        String signature = Connection.getHMAC256Signature("this is an hmac test", "400025f683e43791225c");
        System.out.print(signature + "\n");
        Assert.assertEquals("22ec2da74c9b9abbe00e5681b0bed4b801298af85b385f215937e594595250f3", signature);
    }

    @Before
    public void initMockBitwage() throws IOException, ShapeShiftException, JSONException {
        server = new MockWebServer();
        server.start();

        userkeypair = new UserKeyPair(new JSONObject(GET_SECRET_KEY));
        bitwage = new Bitwage(userkeypair);
        bitwage.baseUrl = server.getUrl("/").toString();
        bitwage.client.setConnectionSpecs(Collections.singletonList(ConnectionSpec.CLEARTEXT));
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
    */

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void loginTest() {

        authentication = new Authentication();
        authentication.baseUrl = server.getUrl("/").toString();
        authentication.client.setConnectionSpecs(Collections.singletonList(ConnectionSpec.CLEARTEXT));

        //Generated hashes, not coinomi actual api keys
        authentication.setApiPublicKey("a429f53a2780bb6cdfa5881dc708ebf1");
        authentication.setSecret("fd6d24e6cb8ee31e751d5ba6db1ddb65");

        server.enqueue(new MockResponse().setBody(UUID));
        String uuid = authentication.login(USERNAME, PASSWORD);

        Assert.assertEquals("d72f22b5-cb00-4168-8f82-50ca35956f3d", uuid);

        server.enqueue(new MockResponse().setBody(GET_SECRET_KEY));

        UserKeyPair userKeyPair = authentication.twofa(USERNAME, uuid, ACCESS_TOKEN);

        Assert.assertEquals("joeshoe@gmail.com", userKeyPair.getUsername());
        Assert.assertEquals("b47a747c2c654adba50f41acd2939511", userKeyPair.getApikey());
        Assert.assertEquals("80fe301033df46efb36355247044bbcb", userKeyPair.getApisecret());
    }

    @Test
    public void tickersTest() throws IOException, ShapeShiftException {
        server.enqueue(new MockResponse().setBody(TICKERS));
        HashMap<String, Double> tickers;
        tickers = bitwage.getTickers().getTickers();
        System.out.print(tickers.toString() + "\n");

    }

    @Test
    public void profileTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(PROFILE));
        Profile profile = bitwage.getProfile();
        Assert.assertEquals("139343039393", profile.getUserid());
        System.out.print(profile.getFirstname() + " " + profile.getLastname() + "\n");
    }

    @Test
    public void companiesTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(COMPANIES));
        Companies companies = bitwage.getCompanies();
        System.out.print(companies.getCompanies().toString());

    }

    @Test
    public void bpicompanyviewTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(BPIEMPLIST));
        List<Employer> employers = bitwage.bpicompanyview();
        System.out.print(employers.toString());
    }

    @Test
    public void bpicompanyeditTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(BPIEMPLIST));
        Employer employer = new Employer(new JSONObject(BPIEMPLOYER));
        List<Employer> employers = bitwage.bpicompanyedit(employer);
        System.out.print(employers.toString());
    }

    @Test
    public void payrollsTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(PAYROLL));
        UserPayrollsInfo payrollinfo = bitwage.getPayrolls();
        System.out.print(payrollinfo.getUserpayrolls().toString());
    }

    @Test
    public void getCompanyByidTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(COMPANY));
        Company comp = bitwage.getCompanyByid(new BigInteger("6403555720167424"));
        Assert.assertEquals("Example Company", comp.getCompany_name());
        System.out.print(comp);
    }

    @Test
    public void getWorkersByCompanyidTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(WORKERLIST));
        WorkersSimple workers = bitwage.getWorkersByCompanyId(new BigInteger("5088651352473600"), 1);
        System.out.print(workers.getWorkerSimpleList().toString());
    }

    @Test
    public void getLinkedAccountsTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(LINKEDACCOUNTS));
        List<LinkedAccount> linkedaccounts = bitwage.getLinkedAccountsByid(new BigInteger("5088651352473600"));
        System.out.print(linkedaccounts.toString());
    }

    @Test
    public void getWorkerByIdTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(WORKER));
        Worker worker  = bitwage.getWorkerById(new BigInteger("5088651352473600"), new BigInteger("5190345373515776"));
        System.out.print(worker.toString());
    }

    @Test
    public void inviteWorkersTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(INVITEWORKERS));
        List<Invite> inviteList= new ArrayList<>();
        inviteList.add(new Invite("example@example.com", "admin"));
        List<InviteLog> inviteLogList  = bitwage.inviteWorkers(new BigInteger("5088651352473600"), inviteList);
        System.out.print(inviteLogList.toString());
    }

    @Test
    public void emailtoIdTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(EMAILTOID));
        List<String> emails= new ArrayList<>();
        emails.add("success@success.com");
        emails.add("failure@failure.com");
        EmailToIdResults results = bitwage.emailtoId(new BigInteger("5088651352473600"), emails);
        System.out.print(results.toString());
    }

    @Test
    public void getCompanyPayrollsTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(COMPANY_PAYROLLS));
        CompanyPayrollInfo companyPayrolls = bitwage.getCompanyPayrolls(new BigInteger("5088651352473600"),1);
        System.out.print(companyPayrolls.toString());
    }

    @Test
    public void getCompanyPayrollByIdTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(COMPANY_PAYROLL));
        CompanyPayroll companyPayroll = bitwage.getCompanyPayrollById(new BigInteger("5088651352473600"),new BigInteger("4838400918028288"),1);
        System.out.print(companyPayroll.toString());
    }

    @Test
    public void getWorkerPayrollsByUserIdTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(GET_WORKER_PAYROLLS));
        WorkerPayrolls workerPayrolls = bitwage.getWorkerPayrollsByUserId(new BigInteger("5088651352473600"),new BigInteger("6523423654986751"),1);
        System.out.print(workerPayrolls.toString());
    }

    @Test
    public void createPayrollTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(CREATE_PAYROLL));
        Map<String, Double> payments = new HashMap<>();
        payments.put("example@example.com", 10.0);
        PayrollCreation payrollCreation = bitwage.createPayroll(new BigInteger("5088651352473600"),false , false, payments);
        System.out.print(payrollCreation.toString());
    }
    
    @Test
    public void setPaymentMethodForPayrollTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(PAYROLL_PAYMENT_METHOD));
        CompanyPaymentMethod paymentmethod = bitwage.setPaymentMethodForPayroll(new BigInteger("5088651352473600"),new BigInteger("5822463824887808"), PaymentMethod.WIRE);
        System.out.print(paymentmethod.toString());
    }
    
    @Test
    public void deletePayrollByIdTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(DELETE_PAYROLL));
        DeletePayrollLog deletepayrolllog = bitwage.deletePayrollById(new BigInteger("5822463824887809"));
        System.out.print(deletepayrolllog.toString());
    }
    
    @Test
    public void getCompanyInvoicesTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(COMPANY_INVOICES));
        CompanyInvoices companyInvoices = bitwage.getCompanyInvoices(new BigInteger("5769603078684622"));
        System.out.println(companyInvoices.toString());
    }
    
    @Test
    public void getCompanyInvoiceByIdTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(INVOICE));
        Invoice invoice = bitwage.getCompanyInvoiceById(new BigInteger("5769603078684622"),new BigInteger("5769603078684633"));
        System.out.println(invoice.toString());
    }
    
    @Test
    public void approveInvoiceTest() throws JSONException, ShapeShiftException, IOException {
        server.enqueue(new MockResponse().setBody(APPROVE_INVOICE));
        InvoiceApproval approval = bitwage.approveInvoice(new BigInteger("5769603078684622"),new BigInteger("2384192341092834"));
        System.out.println(approval.toString());
    }
    
    public static final String APPROVE_INVOICE = "{\n" + 
    		"    \"invoice\": {\n" + 
    		"        \"id\": 5822463824887808,\n" + 
    		"        \"status\": \"approved\", \n" + 
    		"        \"currency\": \"USD\", \n" + 
    		"        \"total_amount_fiat\": 10000.0\n" +
    		"    },\n" + 
    		"    \"payroll\": {\n" + 
    		"        \"id\": 5822463824887833,\n" + 
    		"        \"total_amount\": 10000.0,\n" +
    		"        \"currency\": \"USD\", \n" + 
    		"        \"status\": \"created\", \n" + 
    		"        \"num_suborders\": 1,\n" + 
    		"        \"suborder_list\": [{\n" + 
    		"            \"amount_usd\": \"10000.00\", \n" + 
    		"            \"user_id\": 5707702298738622, \n" + 
    		"            \"email\": \"joasdf@gmail.com\"\n" + 
    		"        }],\n" + 
    		"        \"payment_method\":\"None\",\n" + 
    		"        \"payment_method_set_msg\": \"None or Unsupported Default Payment Method \"\n" + 
    		"    }\n" + 
    		"}";
    
    public static final String INVOICE = "{\n" + 
    		"  \"id\": 5769603078684633,\n" + 
    		"  \"company\": {\n" + 
    		"    \"id\": 5769603078684622,\n" + 
    		"    \"name\": \"Test, Inc\",\n" + 
    		"    \"phone_number\": \"+1 (204) 200-3963\",\n" + 
    		"    \"street_address\": \"123 First Street\",\n" + 
    		"    \"city\": \"Knoxville\",\n" + 
    		"    \"state\": \"TN\",\n" + 
    		"    \"zip\": \"34443\",\n" + 
    		"    \"website_url\": \"http://example.com\",\n" + 
    		"    \"ein\": \"12-1234567\",\n" + 
    		"    \"email\": \"support@example.com\"\n" +
    		"  },\n" + 
    		"  \"worker\": {\n" + 
    		"    \"id\": 5769603078684611,\n" + 
    		"    \"email\": \"test@example.com\"\n" + 
    		"  },\n" + 
    		"  \"line_items\":[{\n" + 
    		"    \"time\": 20.0,\n" + 
    		"    \"amount_fiat\": 1000.0,\n" + 
    		"    \"amountpertime\": 50.0,\n" + 
    		"    \"currency\": \"USD\",\n" + 
    		"    \"description\": \"front end development\"\n" + 
    		"  }],\n" + 
    		"  \"time_created\": \"2016-03-07 22:10:29.049796\",\n" + 
    		"  \"currency\": \"USD\", \n" + 
    		"  \"amount\": 1000.0, \n" + 
    		"  \"due date\": \"2016-03-07\", \n" + 
    		"  \"approved\": true\n" + 
    		"}";
    
    public static final String COMPANY_INVOICES = "{\n" + 
    		"  \"company\": {\n" + 
    		"    \"id\": \"6122080743456768\",\n" + 
    		"    \"name\": \"Hello, Inc.\"\n" + 
    		"  },\n" +
    		"  \"invoices\": \n" + 
    		"    [\n" + 
    		"      {\n" + 
    		"        \"id\": 5725981679550464, \n" + 
    		"        \"worker\": {\n" + 
    		"            \"id\": 6122080743456722,\n" + 
    		"            \"email\": \"test@example.com\"\n" + 
    		"        },\n" + 
    		"        \"total_amount_fiat\": 1000.0, \n" + 
    		"        \"time_created\": \"2016-03-07 22:10:29.049796\", \n" + 
    		"        \"currency\": \"USD\",\n" + 
    		"        \"payroll_id\": 6122080743456555,\n" + 
    		"        \"due_date\": \"2016-03-07\",\n" + 
    		"        \"approved\": false\n" + 
    		"      }\n" + 
    		"    ]\n" + 
    		"}";
    
    public static final String DELETE_PAYROLL="{\n" +
            "  \"success\": [5822463824887809],\n" +
            "  \"failure\": [\n" +
            "    {\n" +
            "      \"payroll_id\": 5822463824887808\n" +
            "    }\n" +
            "  ]\n" +
            "}";
    
    public static final String PAYROLL_PAYMENT_METHOD = "{\n" +
    		"\"status\": \"success\",\n" +
    		"\"payroll_id\": \"5822463824887808\",\n" +
    		"\"payment_method\": \"wire\",\n" +
    		"\"payment_method_set_msg\": \"\"\n" +
    		"}";

    public static final String CREATE_PAYROLL = "{\n" +
            "\"payroll_id\": 5822463824887808, \n" +
            "\"status\":\"created\",\n" +
            "\"suborder_list\": [\n" +
            "{\n" +
            "\"email\": \"example@example.com\",\n" +
            "\"amount_usd\": \"10\"\n" +
            "}\n" +
            "],\n" +
            "\"total_amount\": 10.0,\n" +
            " \"currency\":\"USD\",\n" +
            "\"num_suborders\": 1,\n" +
            "\"payment_method\": \"ach_credit\",\n" +
            "\"payment_method_set_msg\":\"\"\n" +
            "}";

    public static final String GET_WORKER_PAYROLLS= "{\n" +
            "  \"company_id\": \"6403555720167424\",\n" +
            "  \"company_name\": \"Example Company\",\n" +
            "  \"payroll_not_fulfilled\": 33,\n" +
            "  \"payroll_fulfilled\": 0,\n" +
            "  \"total_fulfilled\": 0.0,\n" +
            "  \"total_not_fulfilled\": 10000.06,\n" +
            "  \"total_by_currency\": {\n" +
            "    \"fulfilled\": {\n" +
            "        \"USD\":0.0,\n" +
            "        \"EUR\":0.0\n" +
            "    },\n" +
            "    \"not_fulfilled\": {\n" +
            "        \"USD\":10000.06,\n" +
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
            "               \"user_email\": \"test3@example.com\", \n" +
            "               \"user_id\": 5677071364398842, \n" +
            "               \"userpayroll_id\": 6523423654986751\n" +
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
            "               \"user_email\": \"test3@example.com\", \n" +
            "               \"user_id\": 5677071364398842, \n" +
            "               \"userpayroll_id\": 6523423654986753\n" +
            "           }\n" +
            "       ]\n" +
            "    }\n" +
            "  ],\n" +
            "  \"meta\": {\n" +
            "    \"curr_page\":1,\n" +
            "    \"next_page\":\"\",\n" +
            "    \"total_pages\":1\n" +
            "  }\n" +
            "}";

    private static final String GET_SECRET_KEY = "{ \n" +
            "    \"username\": \"joeshoe@gmail.com\",\n" +
            "    \"apikey\": \"b47a747c2c654adba50f41acd2939511\",\n" +
            "    \"apisecret\": \"80fe301033df46efb36355247044bbcb\"\n" +
            "}";

    private static final String BPIEMPLIST = "{\n" +
            "  \"bpiemplist\": [\n" +
            "    {\n" +
            "      \"ppname\": \"payroll provider name\",\n" +
            "      \"created\": \"2016-09-09 05:33:08.238780\",\n" +
            "      \"ppwebsite\": \"https://www.bitwage.com/\",\n" +
            "      \"employer\": \"test employer\",\n" +
            "      \"employerwebsite\": \"https://www.bitwage.com/\",\n" +
            "      \"employercurrency\": \"USD\",\n" +
            "      \"jobrole\": \"Employee\",\n" +
            "      \"bpionboardid\": 6253195365934046,\n" +
            "      \"order\": 1\n" +
            "    }\n" +
            "  ]\n" +
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

    private static final String TICKERS = "{\n" +
            "    \"XBTUSD\": \"687.45\",\n" +
            "    \"XBTEUR\": \"616.13\",\n" +
            "    \"USDEUR\": \"0.88\",\n" +
            "    \"EURPHP\": \"51.08\",\n" +
            "    \"EURINR\": \"74.84\",\n" +
            "    \"EURVND\": \"24841.15\",\n" +
            "    \"USDVND\": \"22300.00\",\n" +
            "    \"USDINR\": \"67.18\",\n" +
            "    \"EURMXN\": \"20.44\",\n" +
            "    \"EURUSD\": \"1.10\",\n" +
            "    \"datetimeUTC\": \"2016-07-02 08:40:59\",\n" +
            "    \"USDMXN\": \"18.35\",\n" +
            "    \"USDBRL\": \"3.24\",\n" +
            "    \"EURBRL\": \"3.60\",\n" +
            "    \"USDPHP\": \"45.78\"\n" +
            "}";

    private static final String PROFILE = "{\n"+
            "  \"user_id\": \"139343039393\",\n"+
            "  \"first_name\": \"George\",\n"+
            "  \"last_name\": \"Foogleshmidt\",\n"+
            "  \"date_of_birth\": \"02-13-2010\",\n"+
            "  \"phone_number\": \"19123457686\",\n"+
            "  \"street_address\": \"123 First Street\",\n"+
            "  \"city\": \"San Francisco\",\n"+
            "  \"state\": \"CA\",\n"+
            "  \"zip\": \"94120\"\n"+
            "}";

    private static final String COMPANIES = "{\n" +
            "  \"companies\": [\n" +
            "    {\n" +
            "      \"company_id\": 12357567567,\n" +
            "      \"company_name\": \"Najin\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"company_id\": 54358605335,\n" +
            "      \"company_name\": \"Bape\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"default_company\": 12357567567\n" +
            "}";

    private static final String COMPANY = "{\n" +
            "  \"company_name\": \"Example Company\",\n" +
            "  \"company_id\": \"6403555720167424\",\n" +
            "  \"street_address\": \"123 Main St.\",\n" +
            "  \"country\": \"US\",\n" +
            "  \"city\": \"Sunnyvale\",\n" +
            "  \"state\": \"CA\",\n" +
            "  \"zip\": \"12345\",\n" +
            "  \"website_url\": \"http://www.example.com\",\n" +
            "  \"email\": \"example@example.com\",\n" +
            "  \"phone\": \"+1 (123) 456-7891\",\n" +
            "  \"ein\": \"123151244\",\n" +
            "  \"default_payment_method\": \"ach_credit\"\n" +
            "}";

    private static final String UUID = "{ \n" +
            "    \"username\": \"joeshoe@gmail.com\",\n" +
            "    \"uuid\": \"d72f22b5-cb00-4168-8f82-50ca35956f3d\"\n" +
            "}";
}