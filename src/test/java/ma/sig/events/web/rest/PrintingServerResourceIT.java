package ma.sig.events.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import ma.sig.events.IntegrationTest;
import ma.sig.events.domain.Event;
import ma.sig.events.domain.PrintingCentre;
import ma.sig.events.domain.PrintingServer;
import ma.sig.events.repository.PrintingServerRepository;
import ma.sig.events.service.criteria.PrintingServerCriteria;
import ma.sig.events.service.dto.PrintingServerDTO;
import ma.sig.events.service.mapper.PrintingServerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PrintingServerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrintingServerResourceIT {

    private static final String DEFAULT_PRINTING_SERVER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_SERVER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_SERVER_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_SERVER_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_SERVER_HOST = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_SERVER_HOST = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_SERVER_PORT = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_SERVER_PORT = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_SERVER_DNS = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_SERVER_DNS = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_SERVER_PROXY = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_SERVER_PROXY = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_SERVER_PARAM_1 = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_SERVER_PARAM_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_SERVER_PARAM_2 = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_SERVER_PARAM_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_SERVER_PARAM_3 = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_SERVER_PARAM_3 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRINTING_SERVER_STAT = false;
    private static final Boolean UPDATED_PRINTING_SERVER_STAT = true;

    private static final String DEFAULT_PRINTING_SERVER_PARAMS = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_SERVER_PARAMS = "BBBBBBBBBB";

    private static final String DEFAULT_PRINTING_SERVER_ATTRIBUTS = "AAAAAAAAAA";
    private static final String UPDATED_PRINTING_SERVER_ATTRIBUTS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/printing-servers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{printingServerId}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrintingServerRepository printingServerRepository;

    @Autowired
    private PrintingServerMapper printingServerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrintingServerMockMvc;

    private PrintingServer printingServer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrintingServer createEntity(EntityManager em) {
        PrintingServer printingServer = new PrintingServer()
            .printingServerName(DEFAULT_PRINTING_SERVER_NAME)
            .printingServerDescription(DEFAULT_PRINTING_SERVER_DESCRIPTION)
            .printingServerHost(DEFAULT_PRINTING_SERVER_HOST)
            .printingServerPort(DEFAULT_PRINTING_SERVER_PORT)
            .printingServerDns(DEFAULT_PRINTING_SERVER_DNS)
            .printingServerProxy(DEFAULT_PRINTING_SERVER_PROXY)
            .printingServerParam1(DEFAULT_PRINTING_SERVER_PARAM_1)
            .printingServerParam2(DEFAULT_PRINTING_SERVER_PARAM_2)
            .printingServerParam3(DEFAULT_PRINTING_SERVER_PARAM_3)
            .printingServerStat(DEFAULT_PRINTING_SERVER_STAT)
            .printingServerParams(DEFAULT_PRINTING_SERVER_PARAMS)
            .printingServerAttributs(DEFAULT_PRINTING_SERVER_ATTRIBUTS);
        return printingServer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrintingServer createUpdatedEntity(EntityManager em) {
        PrintingServer printingServer = new PrintingServer()
            .printingServerName(UPDATED_PRINTING_SERVER_NAME)
            .printingServerDescription(UPDATED_PRINTING_SERVER_DESCRIPTION)
            .printingServerHost(UPDATED_PRINTING_SERVER_HOST)
            .printingServerPort(UPDATED_PRINTING_SERVER_PORT)
            .printingServerDns(UPDATED_PRINTING_SERVER_DNS)
            .printingServerProxy(UPDATED_PRINTING_SERVER_PROXY)
            .printingServerParam1(UPDATED_PRINTING_SERVER_PARAM_1)
            .printingServerParam2(UPDATED_PRINTING_SERVER_PARAM_2)
            .printingServerParam3(UPDATED_PRINTING_SERVER_PARAM_3)
            .printingServerStat(UPDATED_PRINTING_SERVER_STAT)
            .printingServerParams(UPDATED_PRINTING_SERVER_PARAMS)
            .printingServerAttributs(UPDATED_PRINTING_SERVER_ATTRIBUTS);
        return printingServer;
    }

    @BeforeEach
    public void initTest() {
        printingServer = createEntity(em);
    }

    @Test
    @Transactional
    void createPrintingServer() throws Exception {
        int databaseSizeBeforeCreate = printingServerRepository.findAll().size();
        // Create the PrintingServer
        PrintingServerDTO printingServerDTO = printingServerMapper.toDto(printingServer);
        restPrintingServerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(printingServerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PrintingServer in the database
        List<PrintingServer> printingServerList = printingServerRepository.findAll();
        assertThat(printingServerList).hasSize(databaseSizeBeforeCreate + 1);
        PrintingServer testPrintingServer = printingServerList.get(printingServerList.size() - 1);
        assertThat(testPrintingServer.getPrintingServerName()).isEqualTo(DEFAULT_PRINTING_SERVER_NAME);
        assertThat(testPrintingServer.getPrintingServerDescription()).isEqualTo(DEFAULT_PRINTING_SERVER_DESCRIPTION);
        assertThat(testPrintingServer.getPrintingServerHost()).isEqualTo(DEFAULT_PRINTING_SERVER_HOST);
        assertThat(testPrintingServer.getPrintingServerPort()).isEqualTo(DEFAULT_PRINTING_SERVER_PORT);
        assertThat(testPrintingServer.getPrintingServerDns()).isEqualTo(DEFAULT_PRINTING_SERVER_DNS);
        assertThat(testPrintingServer.getPrintingServerProxy()).isEqualTo(DEFAULT_PRINTING_SERVER_PROXY);
        assertThat(testPrintingServer.getPrintingServerParam1()).isEqualTo(DEFAULT_PRINTING_SERVER_PARAM_1);
        assertThat(testPrintingServer.getPrintingServerParam2()).isEqualTo(DEFAULT_PRINTING_SERVER_PARAM_2);
        assertThat(testPrintingServer.getPrintingServerParam3()).isEqualTo(DEFAULT_PRINTING_SERVER_PARAM_3);
        assertThat(testPrintingServer.getPrintingServerStat()).isEqualTo(DEFAULT_PRINTING_SERVER_STAT);
        assertThat(testPrintingServer.getPrintingServerParams()).isEqualTo(DEFAULT_PRINTING_SERVER_PARAMS);
        assertThat(testPrintingServer.getPrintingServerAttributs()).isEqualTo(DEFAULT_PRINTING_SERVER_ATTRIBUTS);
    }

    @Test
    @Transactional
    void createPrintingServerWithExistingId() throws Exception {
        // Create the PrintingServer with an existing ID
        printingServer.setPrintingServerId(1L);
        PrintingServerDTO printingServerDTO = printingServerMapper.toDto(printingServer);

        int databaseSizeBeforeCreate = printingServerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrintingServerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(printingServerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingServer in the database
        List<PrintingServer> printingServerList = printingServerRepository.findAll();
        assertThat(printingServerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrintingServers() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList
        restPrintingServerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=printingServerId,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].printingServerId").value(hasItem(printingServer.getPrintingServerId().intValue())))
            .andExpect(jsonPath("$.[*].printingServerName").value(hasItem(DEFAULT_PRINTING_SERVER_NAME)))
            .andExpect(jsonPath("$.[*].printingServerDescription").value(hasItem(DEFAULT_PRINTING_SERVER_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].printingServerHost").value(hasItem(DEFAULT_PRINTING_SERVER_HOST)))
            .andExpect(jsonPath("$.[*].printingServerPort").value(hasItem(DEFAULT_PRINTING_SERVER_PORT)))
            .andExpect(jsonPath("$.[*].printingServerDns").value(hasItem(DEFAULT_PRINTING_SERVER_DNS)))
            .andExpect(jsonPath("$.[*].printingServerProxy").value(hasItem(DEFAULT_PRINTING_SERVER_PROXY)))
            .andExpect(jsonPath("$.[*].printingServerParam1").value(hasItem(DEFAULT_PRINTING_SERVER_PARAM_1)))
            .andExpect(jsonPath("$.[*].printingServerParam2").value(hasItem(DEFAULT_PRINTING_SERVER_PARAM_2)))
            .andExpect(jsonPath("$.[*].printingServerParam3").value(hasItem(DEFAULT_PRINTING_SERVER_PARAM_3)))
            .andExpect(jsonPath("$.[*].printingServerStat").value(hasItem(DEFAULT_PRINTING_SERVER_STAT.booleanValue())))
            .andExpect(jsonPath("$.[*].printingServerParams").value(hasItem(DEFAULT_PRINTING_SERVER_PARAMS)))
            .andExpect(jsonPath("$.[*].printingServerAttributs").value(hasItem(DEFAULT_PRINTING_SERVER_ATTRIBUTS)));
    }

    @Test
    @Transactional
    void getPrintingServer() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get the printingServer
        restPrintingServerMockMvc
            .perform(get(ENTITY_API_URL_ID, printingServer.getPrintingServerId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.printingServerId").value(printingServer.getPrintingServerId().intValue()))
            .andExpect(jsonPath("$.printingServerName").value(DEFAULT_PRINTING_SERVER_NAME))
            .andExpect(jsonPath("$.printingServerDescription").value(DEFAULT_PRINTING_SERVER_DESCRIPTION))
            .andExpect(jsonPath("$.printingServerHost").value(DEFAULT_PRINTING_SERVER_HOST))
            .andExpect(jsonPath("$.printingServerPort").value(DEFAULT_PRINTING_SERVER_PORT))
            .andExpect(jsonPath("$.printingServerDns").value(DEFAULT_PRINTING_SERVER_DNS))
            .andExpect(jsonPath("$.printingServerProxy").value(DEFAULT_PRINTING_SERVER_PROXY))
            .andExpect(jsonPath("$.printingServerParam1").value(DEFAULT_PRINTING_SERVER_PARAM_1))
            .andExpect(jsonPath("$.printingServerParam2").value(DEFAULT_PRINTING_SERVER_PARAM_2))
            .andExpect(jsonPath("$.printingServerParam3").value(DEFAULT_PRINTING_SERVER_PARAM_3))
            .andExpect(jsonPath("$.printingServerStat").value(DEFAULT_PRINTING_SERVER_STAT.booleanValue()))
            .andExpect(jsonPath("$.printingServerParams").value(DEFAULT_PRINTING_SERVER_PARAMS))
            .andExpect(jsonPath("$.printingServerAttributs").value(DEFAULT_PRINTING_SERVER_ATTRIBUTS));
    }

    @Test
    @Transactional
    void getPrintingServersByIdFiltering() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        Long id = printingServer.getPrintingServerId();

        defaultPrintingServerShouldBeFound("printingServerId.equals=" + id);
        defaultPrintingServerShouldNotBeFound("printingServerId.notEquals=" + id);

        defaultPrintingServerShouldBeFound("printingServerId.greaterThanOrEqual=" + id);
        defaultPrintingServerShouldNotBeFound("printingServerId.greaterThan=" + id);

        defaultPrintingServerShouldBeFound("printingServerId.lessThanOrEqual=" + id);
        defaultPrintingServerShouldNotBeFound("printingServerId.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerName equals to DEFAULT_PRINTING_SERVER_NAME
        defaultPrintingServerShouldBeFound("printingServerName.equals=" + DEFAULT_PRINTING_SERVER_NAME);

        // Get all the printingServerList where printingServerName equals to UPDATED_PRINTING_SERVER_NAME
        defaultPrintingServerShouldNotBeFound("printingServerName.equals=" + UPDATED_PRINTING_SERVER_NAME);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerNameIsInShouldWork() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerName in DEFAULT_PRINTING_SERVER_NAME or UPDATED_PRINTING_SERVER_NAME
        defaultPrintingServerShouldBeFound("printingServerName.in=" + DEFAULT_PRINTING_SERVER_NAME + "," + UPDATED_PRINTING_SERVER_NAME);

        // Get all the printingServerList where printingServerName equals to UPDATED_PRINTING_SERVER_NAME
        defaultPrintingServerShouldNotBeFound("printingServerName.in=" + UPDATED_PRINTING_SERVER_NAME);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerName is not null
        defaultPrintingServerShouldBeFound("printingServerName.specified=true");

        // Get all the printingServerList where printingServerName is null
        defaultPrintingServerShouldNotBeFound("printingServerName.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerNameContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerName contains DEFAULT_PRINTING_SERVER_NAME
        defaultPrintingServerShouldBeFound("printingServerName.contains=" + DEFAULT_PRINTING_SERVER_NAME);

        // Get all the printingServerList where printingServerName contains UPDATED_PRINTING_SERVER_NAME
        defaultPrintingServerShouldNotBeFound("printingServerName.contains=" + UPDATED_PRINTING_SERVER_NAME);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerNameNotContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerName does not contain DEFAULT_PRINTING_SERVER_NAME
        defaultPrintingServerShouldNotBeFound("printingServerName.doesNotContain=" + DEFAULT_PRINTING_SERVER_NAME);

        // Get all the printingServerList where printingServerName does not contain UPDATED_PRINTING_SERVER_NAME
        defaultPrintingServerShouldBeFound("printingServerName.doesNotContain=" + UPDATED_PRINTING_SERVER_NAME);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerDescription equals to DEFAULT_PRINTING_SERVER_DESCRIPTION
        defaultPrintingServerShouldBeFound("printingServerDescription.equals=" + DEFAULT_PRINTING_SERVER_DESCRIPTION);

        // Get all the printingServerList where printingServerDescription equals to UPDATED_PRINTING_SERVER_DESCRIPTION
        defaultPrintingServerShouldNotBeFound("printingServerDescription.equals=" + UPDATED_PRINTING_SERVER_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerDescription in DEFAULT_PRINTING_SERVER_DESCRIPTION or UPDATED_PRINTING_SERVER_DESCRIPTION
        defaultPrintingServerShouldBeFound(
            "printingServerDescription.in=" + DEFAULT_PRINTING_SERVER_DESCRIPTION + "," + UPDATED_PRINTING_SERVER_DESCRIPTION
        );

        // Get all the printingServerList where printingServerDescription equals to UPDATED_PRINTING_SERVER_DESCRIPTION
        defaultPrintingServerShouldNotBeFound("printingServerDescription.in=" + UPDATED_PRINTING_SERVER_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerDescription is not null
        defaultPrintingServerShouldBeFound("printingServerDescription.specified=true");

        // Get all the printingServerList where printingServerDescription is null
        defaultPrintingServerShouldNotBeFound("printingServerDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerDescriptionContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerDescription contains DEFAULT_PRINTING_SERVER_DESCRIPTION
        defaultPrintingServerShouldBeFound("printingServerDescription.contains=" + DEFAULT_PRINTING_SERVER_DESCRIPTION);

        // Get all the printingServerList where printingServerDescription contains UPDATED_PRINTING_SERVER_DESCRIPTION
        defaultPrintingServerShouldNotBeFound("printingServerDescription.contains=" + UPDATED_PRINTING_SERVER_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerDescription does not contain DEFAULT_PRINTING_SERVER_DESCRIPTION
        defaultPrintingServerShouldNotBeFound("printingServerDescription.doesNotContain=" + DEFAULT_PRINTING_SERVER_DESCRIPTION);

        // Get all the printingServerList where printingServerDescription does not contain UPDATED_PRINTING_SERVER_DESCRIPTION
        defaultPrintingServerShouldBeFound("printingServerDescription.doesNotContain=" + UPDATED_PRINTING_SERVER_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerHostIsEqualToSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerHost equals to DEFAULT_PRINTING_SERVER_HOST
        defaultPrintingServerShouldBeFound("printingServerHost.equals=" + DEFAULT_PRINTING_SERVER_HOST);

        // Get all the printingServerList where printingServerHost equals to UPDATED_PRINTING_SERVER_HOST
        defaultPrintingServerShouldNotBeFound("printingServerHost.equals=" + UPDATED_PRINTING_SERVER_HOST);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerHostIsInShouldWork() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerHost in DEFAULT_PRINTING_SERVER_HOST or UPDATED_PRINTING_SERVER_HOST
        defaultPrintingServerShouldBeFound("printingServerHost.in=" + DEFAULT_PRINTING_SERVER_HOST + "," + UPDATED_PRINTING_SERVER_HOST);

        // Get all the printingServerList where printingServerHost equals to UPDATED_PRINTING_SERVER_HOST
        defaultPrintingServerShouldNotBeFound("printingServerHost.in=" + UPDATED_PRINTING_SERVER_HOST);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerHostIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerHost is not null
        defaultPrintingServerShouldBeFound("printingServerHost.specified=true");

        // Get all the printingServerList where printingServerHost is null
        defaultPrintingServerShouldNotBeFound("printingServerHost.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerHostContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerHost contains DEFAULT_PRINTING_SERVER_HOST
        defaultPrintingServerShouldBeFound("printingServerHost.contains=" + DEFAULT_PRINTING_SERVER_HOST);

        // Get all the printingServerList where printingServerHost contains UPDATED_PRINTING_SERVER_HOST
        defaultPrintingServerShouldNotBeFound("printingServerHost.contains=" + UPDATED_PRINTING_SERVER_HOST);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerHostNotContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerHost does not contain DEFAULT_PRINTING_SERVER_HOST
        defaultPrintingServerShouldNotBeFound("printingServerHost.doesNotContain=" + DEFAULT_PRINTING_SERVER_HOST);

        // Get all the printingServerList where printingServerHost does not contain UPDATED_PRINTING_SERVER_HOST
        defaultPrintingServerShouldBeFound("printingServerHost.doesNotContain=" + UPDATED_PRINTING_SERVER_HOST);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerPortIsEqualToSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerPort equals to DEFAULT_PRINTING_SERVER_PORT
        defaultPrintingServerShouldBeFound("printingServerPort.equals=" + DEFAULT_PRINTING_SERVER_PORT);

        // Get all the printingServerList where printingServerPort equals to UPDATED_PRINTING_SERVER_PORT
        defaultPrintingServerShouldNotBeFound("printingServerPort.equals=" + UPDATED_PRINTING_SERVER_PORT);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerPortIsInShouldWork() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerPort in DEFAULT_PRINTING_SERVER_PORT or UPDATED_PRINTING_SERVER_PORT
        defaultPrintingServerShouldBeFound("printingServerPort.in=" + DEFAULT_PRINTING_SERVER_PORT + "," + UPDATED_PRINTING_SERVER_PORT);

        // Get all the printingServerList where printingServerPort equals to UPDATED_PRINTING_SERVER_PORT
        defaultPrintingServerShouldNotBeFound("printingServerPort.in=" + UPDATED_PRINTING_SERVER_PORT);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerPortIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerPort is not null
        defaultPrintingServerShouldBeFound("printingServerPort.specified=true");

        // Get all the printingServerList where printingServerPort is null
        defaultPrintingServerShouldNotBeFound("printingServerPort.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerPortContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerPort contains DEFAULT_PRINTING_SERVER_PORT
        defaultPrintingServerShouldBeFound("printingServerPort.contains=" + DEFAULT_PRINTING_SERVER_PORT);

        // Get all the printingServerList where printingServerPort contains UPDATED_PRINTING_SERVER_PORT
        defaultPrintingServerShouldNotBeFound("printingServerPort.contains=" + UPDATED_PRINTING_SERVER_PORT);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerPortNotContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerPort does not contain DEFAULT_PRINTING_SERVER_PORT
        defaultPrintingServerShouldNotBeFound("printingServerPort.doesNotContain=" + DEFAULT_PRINTING_SERVER_PORT);

        // Get all the printingServerList where printingServerPort does not contain UPDATED_PRINTING_SERVER_PORT
        defaultPrintingServerShouldBeFound("printingServerPort.doesNotContain=" + UPDATED_PRINTING_SERVER_PORT);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerDnsIsEqualToSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerDns equals to DEFAULT_PRINTING_SERVER_DNS
        defaultPrintingServerShouldBeFound("printingServerDns.equals=" + DEFAULT_PRINTING_SERVER_DNS);

        // Get all the printingServerList where printingServerDns equals to UPDATED_PRINTING_SERVER_DNS
        defaultPrintingServerShouldNotBeFound("printingServerDns.equals=" + UPDATED_PRINTING_SERVER_DNS);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerDnsIsInShouldWork() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerDns in DEFAULT_PRINTING_SERVER_DNS or UPDATED_PRINTING_SERVER_DNS
        defaultPrintingServerShouldBeFound("printingServerDns.in=" + DEFAULT_PRINTING_SERVER_DNS + "," + UPDATED_PRINTING_SERVER_DNS);

        // Get all the printingServerList where printingServerDns equals to UPDATED_PRINTING_SERVER_DNS
        defaultPrintingServerShouldNotBeFound("printingServerDns.in=" + UPDATED_PRINTING_SERVER_DNS);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerDnsIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerDns is not null
        defaultPrintingServerShouldBeFound("printingServerDns.specified=true");

        // Get all the printingServerList where printingServerDns is null
        defaultPrintingServerShouldNotBeFound("printingServerDns.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerDnsContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerDns contains DEFAULT_PRINTING_SERVER_DNS
        defaultPrintingServerShouldBeFound("printingServerDns.contains=" + DEFAULT_PRINTING_SERVER_DNS);

        // Get all the printingServerList where printingServerDns contains UPDATED_PRINTING_SERVER_DNS
        defaultPrintingServerShouldNotBeFound("printingServerDns.contains=" + UPDATED_PRINTING_SERVER_DNS);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerDnsNotContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerDns does not contain DEFAULT_PRINTING_SERVER_DNS
        defaultPrintingServerShouldNotBeFound("printingServerDns.doesNotContain=" + DEFAULT_PRINTING_SERVER_DNS);

        // Get all the printingServerList where printingServerDns does not contain UPDATED_PRINTING_SERVER_DNS
        defaultPrintingServerShouldBeFound("printingServerDns.doesNotContain=" + UPDATED_PRINTING_SERVER_DNS);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerProxyIsEqualToSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerProxy equals to DEFAULT_PRINTING_SERVER_PROXY
        defaultPrintingServerShouldBeFound("printingServerProxy.equals=" + DEFAULT_PRINTING_SERVER_PROXY);

        // Get all the printingServerList where printingServerProxy equals to UPDATED_PRINTING_SERVER_PROXY
        defaultPrintingServerShouldNotBeFound("printingServerProxy.equals=" + UPDATED_PRINTING_SERVER_PROXY);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerProxyIsInShouldWork() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerProxy in DEFAULT_PRINTING_SERVER_PROXY or UPDATED_PRINTING_SERVER_PROXY
        defaultPrintingServerShouldBeFound("printingServerProxy.in=" + DEFAULT_PRINTING_SERVER_PROXY + "," + UPDATED_PRINTING_SERVER_PROXY);

        // Get all the printingServerList where printingServerProxy equals to UPDATED_PRINTING_SERVER_PROXY
        defaultPrintingServerShouldNotBeFound("printingServerProxy.in=" + UPDATED_PRINTING_SERVER_PROXY);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerProxyIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerProxy is not null
        defaultPrintingServerShouldBeFound("printingServerProxy.specified=true");

        // Get all the printingServerList where printingServerProxy is null
        defaultPrintingServerShouldNotBeFound("printingServerProxy.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerProxyContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerProxy contains DEFAULT_PRINTING_SERVER_PROXY
        defaultPrintingServerShouldBeFound("printingServerProxy.contains=" + DEFAULT_PRINTING_SERVER_PROXY);

        // Get all the printingServerList where printingServerProxy contains UPDATED_PRINTING_SERVER_PROXY
        defaultPrintingServerShouldNotBeFound("printingServerProxy.contains=" + UPDATED_PRINTING_SERVER_PROXY);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerProxyNotContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerProxy does not contain DEFAULT_PRINTING_SERVER_PROXY
        defaultPrintingServerShouldNotBeFound("printingServerProxy.doesNotContain=" + DEFAULT_PRINTING_SERVER_PROXY);

        // Get all the printingServerList where printingServerProxy does not contain UPDATED_PRINTING_SERVER_PROXY
        defaultPrintingServerShouldBeFound("printingServerProxy.doesNotContain=" + UPDATED_PRINTING_SERVER_PROXY);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParam1IsEqualToSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParam1 equals to DEFAULT_PRINTING_SERVER_PARAM_1
        defaultPrintingServerShouldBeFound("printingServerParam1.equals=" + DEFAULT_PRINTING_SERVER_PARAM_1);

        // Get all the printingServerList where printingServerParam1 equals to UPDATED_PRINTING_SERVER_PARAM_1
        defaultPrintingServerShouldNotBeFound("printingServerParam1.equals=" + UPDATED_PRINTING_SERVER_PARAM_1);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParam1IsInShouldWork() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParam1 in DEFAULT_PRINTING_SERVER_PARAM_1 or UPDATED_PRINTING_SERVER_PARAM_1
        defaultPrintingServerShouldBeFound(
            "printingServerParam1.in=" + DEFAULT_PRINTING_SERVER_PARAM_1 + "," + UPDATED_PRINTING_SERVER_PARAM_1
        );

        // Get all the printingServerList where printingServerParam1 equals to UPDATED_PRINTING_SERVER_PARAM_1
        defaultPrintingServerShouldNotBeFound("printingServerParam1.in=" + UPDATED_PRINTING_SERVER_PARAM_1);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParam1IsNullOrNotNull() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParam1 is not null
        defaultPrintingServerShouldBeFound("printingServerParam1.specified=true");

        // Get all the printingServerList where printingServerParam1 is null
        defaultPrintingServerShouldNotBeFound("printingServerParam1.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParam1ContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParam1 contains DEFAULT_PRINTING_SERVER_PARAM_1
        defaultPrintingServerShouldBeFound("printingServerParam1.contains=" + DEFAULT_PRINTING_SERVER_PARAM_1);

        // Get all the printingServerList where printingServerParam1 contains UPDATED_PRINTING_SERVER_PARAM_1
        defaultPrintingServerShouldNotBeFound("printingServerParam1.contains=" + UPDATED_PRINTING_SERVER_PARAM_1);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParam1NotContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParam1 does not contain DEFAULT_PRINTING_SERVER_PARAM_1
        defaultPrintingServerShouldNotBeFound("printingServerParam1.doesNotContain=" + DEFAULT_PRINTING_SERVER_PARAM_1);

        // Get all the printingServerList where printingServerParam1 does not contain UPDATED_PRINTING_SERVER_PARAM_1
        defaultPrintingServerShouldBeFound("printingServerParam1.doesNotContain=" + UPDATED_PRINTING_SERVER_PARAM_1);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParam2IsEqualToSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParam2 equals to DEFAULT_PRINTING_SERVER_PARAM_2
        defaultPrintingServerShouldBeFound("printingServerParam2.equals=" + DEFAULT_PRINTING_SERVER_PARAM_2);

        // Get all the printingServerList where printingServerParam2 equals to UPDATED_PRINTING_SERVER_PARAM_2
        defaultPrintingServerShouldNotBeFound("printingServerParam2.equals=" + UPDATED_PRINTING_SERVER_PARAM_2);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParam2IsInShouldWork() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParam2 in DEFAULT_PRINTING_SERVER_PARAM_2 or UPDATED_PRINTING_SERVER_PARAM_2
        defaultPrintingServerShouldBeFound(
            "printingServerParam2.in=" + DEFAULT_PRINTING_SERVER_PARAM_2 + "," + UPDATED_PRINTING_SERVER_PARAM_2
        );

        // Get all the printingServerList where printingServerParam2 equals to UPDATED_PRINTING_SERVER_PARAM_2
        defaultPrintingServerShouldNotBeFound("printingServerParam2.in=" + UPDATED_PRINTING_SERVER_PARAM_2);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParam2IsNullOrNotNull() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParam2 is not null
        defaultPrintingServerShouldBeFound("printingServerParam2.specified=true");

        // Get all the printingServerList where printingServerParam2 is null
        defaultPrintingServerShouldNotBeFound("printingServerParam2.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParam2ContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParam2 contains DEFAULT_PRINTING_SERVER_PARAM_2
        defaultPrintingServerShouldBeFound("printingServerParam2.contains=" + DEFAULT_PRINTING_SERVER_PARAM_2);

        // Get all the printingServerList where printingServerParam2 contains UPDATED_PRINTING_SERVER_PARAM_2
        defaultPrintingServerShouldNotBeFound("printingServerParam2.contains=" + UPDATED_PRINTING_SERVER_PARAM_2);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParam2NotContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParam2 does not contain DEFAULT_PRINTING_SERVER_PARAM_2
        defaultPrintingServerShouldNotBeFound("printingServerParam2.doesNotContain=" + DEFAULT_PRINTING_SERVER_PARAM_2);

        // Get all the printingServerList where printingServerParam2 does not contain UPDATED_PRINTING_SERVER_PARAM_2
        defaultPrintingServerShouldBeFound("printingServerParam2.doesNotContain=" + UPDATED_PRINTING_SERVER_PARAM_2);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParam3IsEqualToSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParam3 equals to DEFAULT_PRINTING_SERVER_PARAM_3
        defaultPrintingServerShouldBeFound("printingServerParam3.equals=" + DEFAULT_PRINTING_SERVER_PARAM_3);

        // Get all the printingServerList where printingServerParam3 equals to UPDATED_PRINTING_SERVER_PARAM_3
        defaultPrintingServerShouldNotBeFound("printingServerParam3.equals=" + UPDATED_PRINTING_SERVER_PARAM_3);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParam3IsInShouldWork() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParam3 in DEFAULT_PRINTING_SERVER_PARAM_3 or UPDATED_PRINTING_SERVER_PARAM_3
        defaultPrintingServerShouldBeFound(
            "printingServerParam3.in=" + DEFAULT_PRINTING_SERVER_PARAM_3 + "," + UPDATED_PRINTING_SERVER_PARAM_3
        );

        // Get all the printingServerList where printingServerParam3 equals to UPDATED_PRINTING_SERVER_PARAM_3
        defaultPrintingServerShouldNotBeFound("printingServerParam3.in=" + UPDATED_PRINTING_SERVER_PARAM_3);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParam3IsNullOrNotNull() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParam3 is not null
        defaultPrintingServerShouldBeFound("printingServerParam3.specified=true");

        // Get all the printingServerList where printingServerParam3 is null
        defaultPrintingServerShouldNotBeFound("printingServerParam3.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParam3ContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParam3 contains DEFAULT_PRINTING_SERVER_PARAM_3
        defaultPrintingServerShouldBeFound("printingServerParam3.contains=" + DEFAULT_PRINTING_SERVER_PARAM_3);

        // Get all the printingServerList where printingServerParam3 contains UPDATED_PRINTING_SERVER_PARAM_3
        defaultPrintingServerShouldNotBeFound("printingServerParam3.contains=" + UPDATED_PRINTING_SERVER_PARAM_3);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParam3NotContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParam3 does not contain DEFAULT_PRINTING_SERVER_PARAM_3
        defaultPrintingServerShouldNotBeFound("printingServerParam3.doesNotContain=" + DEFAULT_PRINTING_SERVER_PARAM_3);

        // Get all the printingServerList where printingServerParam3 does not contain UPDATED_PRINTING_SERVER_PARAM_3
        defaultPrintingServerShouldBeFound("printingServerParam3.doesNotContain=" + UPDATED_PRINTING_SERVER_PARAM_3);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerStatIsEqualToSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerStat equals to DEFAULT_PRINTING_SERVER_STAT
        defaultPrintingServerShouldBeFound("printingServerStat.equals=" + DEFAULT_PRINTING_SERVER_STAT);

        // Get all the printingServerList where printingServerStat equals to UPDATED_PRINTING_SERVER_STAT
        defaultPrintingServerShouldNotBeFound("printingServerStat.equals=" + UPDATED_PRINTING_SERVER_STAT);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerStatIsInShouldWork() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerStat in DEFAULT_PRINTING_SERVER_STAT or UPDATED_PRINTING_SERVER_STAT
        defaultPrintingServerShouldBeFound("printingServerStat.in=" + DEFAULT_PRINTING_SERVER_STAT + "," + UPDATED_PRINTING_SERVER_STAT);

        // Get all the printingServerList where printingServerStat equals to UPDATED_PRINTING_SERVER_STAT
        defaultPrintingServerShouldNotBeFound("printingServerStat.in=" + UPDATED_PRINTING_SERVER_STAT);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerStatIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerStat is not null
        defaultPrintingServerShouldBeFound("printingServerStat.specified=true");

        // Get all the printingServerList where printingServerStat is null
        defaultPrintingServerShouldNotBeFound("printingServerStat.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParamsIsEqualToSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParams equals to DEFAULT_PRINTING_SERVER_PARAMS
        defaultPrintingServerShouldBeFound("printingServerParams.equals=" + DEFAULT_PRINTING_SERVER_PARAMS);

        // Get all the printingServerList where printingServerParams equals to UPDATED_PRINTING_SERVER_PARAMS
        defaultPrintingServerShouldNotBeFound("printingServerParams.equals=" + UPDATED_PRINTING_SERVER_PARAMS);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParamsIsInShouldWork() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParams in DEFAULT_PRINTING_SERVER_PARAMS or UPDATED_PRINTING_SERVER_PARAMS
        defaultPrintingServerShouldBeFound(
            "printingServerParams.in=" + DEFAULT_PRINTING_SERVER_PARAMS + "," + UPDATED_PRINTING_SERVER_PARAMS
        );

        // Get all the printingServerList where printingServerParams equals to UPDATED_PRINTING_SERVER_PARAMS
        defaultPrintingServerShouldNotBeFound("printingServerParams.in=" + UPDATED_PRINTING_SERVER_PARAMS);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParamsIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParams is not null
        defaultPrintingServerShouldBeFound("printingServerParams.specified=true");

        // Get all the printingServerList where printingServerParams is null
        defaultPrintingServerShouldNotBeFound("printingServerParams.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParamsContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParams contains DEFAULT_PRINTING_SERVER_PARAMS
        defaultPrintingServerShouldBeFound("printingServerParams.contains=" + DEFAULT_PRINTING_SERVER_PARAMS);

        // Get all the printingServerList where printingServerParams contains UPDATED_PRINTING_SERVER_PARAMS
        defaultPrintingServerShouldNotBeFound("printingServerParams.contains=" + UPDATED_PRINTING_SERVER_PARAMS);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerParamsNotContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerParams does not contain DEFAULT_PRINTING_SERVER_PARAMS
        defaultPrintingServerShouldNotBeFound("printingServerParams.doesNotContain=" + DEFAULT_PRINTING_SERVER_PARAMS);

        // Get all the printingServerList where printingServerParams does not contain UPDATED_PRINTING_SERVER_PARAMS
        defaultPrintingServerShouldBeFound("printingServerParams.doesNotContain=" + UPDATED_PRINTING_SERVER_PARAMS);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerAttributsIsEqualToSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerAttributs equals to DEFAULT_PRINTING_SERVER_ATTRIBUTS
        defaultPrintingServerShouldBeFound("printingServerAttributs.equals=" + DEFAULT_PRINTING_SERVER_ATTRIBUTS);

        // Get all the printingServerList where printingServerAttributs equals to UPDATED_PRINTING_SERVER_ATTRIBUTS
        defaultPrintingServerShouldNotBeFound("printingServerAttributs.equals=" + UPDATED_PRINTING_SERVER_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerAttributsIsInShouldWork() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerAttributs in DEFAULT_PRINTING_SERVER_ATTRIBUTS or UPDATED_PRINTING_SERVER_ATTRIBUTS
        defaultPrintingServerShouldBeFound(
            "printingServerAttributs.in=" + DEFAULT_PRINTING_SERVER_ATTRIBUTS + "," + UPDATED_PRINTING_SERVER_ATTRIBUTS
        );

        // Get all the printingServerList where printingServerAttributs equals to UPDATED_PRINTING_SERVER_ATTRIBUTS
        defaultPrintingServerShouldNotBeFound("printingServerAttributs.in=" + UPDATED_PRINTING_SERVER_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerAttributsIsNullOrNotNull() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerAttributs is not null
        defaultPrintingServerShouldBeFound("printingServerAttributs.specified=true");

        // Get all the printingServerList where printingServerAttributs is null
        defaultPrintingServerShouldNotBeFound("printingServerAttributs.specified=false");
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerAttributsContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerAttributs contains DEFAULT_PRINTING_SERVER_ATTRIBUTS
        defaultPrintingServerShouldBeFound("printingServerAttributs.contains=" + DEFAULT_PRINTING_SERVER_ATTRIBUTS);

        // Get all the printingServerList where printingServerAttributs contains UPDATED_PRINTING_SERVER_ATTRIBUTS
        defaultPrintingServerShouldNotBeFound("printingServerAttributs.contains=" + UPDATED_PRINTING_SERVER_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingServerAttributsNotContainsSomething() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        // Get all the printingServerList where printingServerAttributs does not contain DEFAULT_PRINTING_SERVER_ATTRIBUTS
        defaultPrintingServerShouldNotBeFound("printingServerAttributs.doesNotContain=" + DEFAULT_PRINTING_SERVER_ATTRIBUTS);

        // Get all the printingServerList where printingServerAttributs does not contain UPDATED_PRINTING_SERVER_ATTRIBUTS
        defaultPrintingServerShouldBeFound("printingServerAttributs.doesNotContain=" + UPDATED_PRINTING_SERVER_ATTRIBUTS);
    }

    @Test
    @Transactional
    void getAllPrintingServersByPrintingCentreIsEqualToSomething() throws Exception {
        PrintingCentre printingCentre;
        if (TestUtil.findAll(em, PrintingCentre.class).isEmpty()) {
            printingServerRepository.saveAndFlush(printingServer);
            printingCentre = PrintingCentreResourceIT.createEntity(em);
        } else {
            printingCentre = TestUtil.findAll(em, PrintingCentre.class).get(0);
        }
        em.persist(printingCentre);
        em.flush();
        printingServer.addPrintingCentre(printingCentre);
        printingServerRepository.saveAndFlush(printingServer);
        Long printingCentreId = printingCentre.getPrintingCentreId();

        // Get all the printingServerList where printingCentre equals to printingCentreId
        defaultPrintingServerShouldBeFound("printingCentreId.equals=" + printingCentreId);

        // Get all the printingServerList where printingCentre equals to (printingCentreId + 1)
        defaultPrintingServerShouldNotBeFound("printingCentreId.equals=" + (printingCentreId + 1));
    }

    @Test
    @Transactional
    void getAllPrintingServersByEventIsEqualToSomething() throws Exception {
        Event event;
        if (TestUtil.findAll(em, Event.class).isEmpty()) {
            printingServerRepository.saveAndFlush(printingServer);
            event = EventResourceIT.createEntity(em);
        } else {
            event = TestUtil.findAll(em, Event.class).get(0);
        }
        em.persist(event);
        em.flush();
        printingServer.setEvent(event);
        printingServerRepository.saveAndFlush(printingServer);
        Long eventId = event.getEventId();

        // Get all the printingServerList where event equals to eventId
        defaultPrintingServerShouldBeFound("eventId.equals=" + eventId);

        // Get all the printingServerList where event equals to (eventId + 1)
        defaultPrintingServerShouldNotBeFound("eventId.equals=" + (eventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPrintingServerShouldBeFound(String filter) throws Exception {
        restPrintingServerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=printingServerId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].printingServerId").value(hasItem(printingServer.getPrintingServerId().intValue())))
            .andExpect(jsonPath("$.[*].printingServerName").value(hasItem(DEFAULT_PRINTING_SERVER_NAME)))
            .andExpect(jsonPath("$.[*].printingServerDescription").value(hasItem(DEFAULT_PRINTING_SERVER_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].printingServerHost").value(hasItem(DEFAULT_PRINTING_SERVER_HOST)))
            .andExpect(jsonPath("$.[*].printingServerPort").value(hasItem(DEFAULT_PRINTING_SERVER_PORT)))
            .andExpect(jsonPath("$.[*].printingServerDns").value(hasItem(DEFAULT_PRINTING_SERVER_DNS)))
            .andExpect(jsonPath("$.[*].printingServerProxy").value(hasItem(DEFAULT_PRINTING_SERVER_PROXY)))
            .andExpect(jsonPath("$.[*].printingServerParam1").value(hasItem(DEFAULT_PRINTING_SERVER_PARAM_1)))
            .andExpect(jsonPath("$.[*].printingServerParam2").value(hasItem(DEFAULT_PRINTING_SERVER_PARAM_2)))
            .andExpect(jsonPath("$.[*].printingServerParam3").value(hasItem(DEFAULT_PRINTING_SERVER_PARAM_3)))
            .andExpect(jsonPath("$.[*].printingServerStat").value(hasItem(DEFAULT_PRINTING_SERVER_STAT.booleanValue())))
            .andExpect(jsonPath("$.[*].printingServerParams").value(hasItem(DEFAULT_PRINTING_SERVER_PARAMS)))
            .andExpect(jsonPath("$.[*].printingServerAttributs").value(hasItem(DEFAULT_PRINTING_SERVER_ATTRIBUTS)));

        // Check, that the count call also returns 1
        restPrintingServerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=printingServerId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPrintingServerShouldNotBeFound(String filter) throws Exception {
        restPrintingServerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=printingServerId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPrintingServerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=printingServerId,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPrintingServer() throws Exception {
        // Get the printingServer
        restPrintingServerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrintingServer() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        int databaseSizeBeforeUpdate = printingServerRepository.findAll().size();

        // Update the printingServer
        PrintingServer updatedPrintingServer = printingServerRepository.findById(printingServer.getPrintingServerId()).get();
        // Disconnect from session so that the updates on updatedPrintingServer are not directly saved in db
        em.detach(updatedPrintingServer);
        updatedPrintingServer
            .printingServerName(UPDATED_PRINTING_SERVER_NAME)
            .printingServerDescription(UPDATED_PRINTING_SERVER_DESCRIPTION)
            .printingServerHost(UPDATED_PRINTING_SERVER_HOST)
            .printingServerPort(UPDATED_PRINTING_SERVER_PORT)
            .printingServerDns(UPDATED_PRINTING_SERVER_DNS)
            .printingServerProxy(UPDATED_PRINTING_SERVER_PROXY)
            .printingServerParam1(UPDATED_PRINTING_SERVER_PARAM_1)
            .printingServerParam2(UPDATED_PRINTING_SERVER_PARAM_2)
            .printingServerParam3(UPDATED_PRINTING_SERVER_PARAM_3)
            .printingServerStat(UPDATED_PRINTING_SERVER_STAT)
            .printingServerParams(UPDATED_PRINTING_SERVER_PARAMS)
            .printingServerAttributs(UPDATED_PRINTING_SERVER_ATTRIBUTS);
        PrintingServerDTO printingServerDTO = printingServerMapper.toDto(updatedPrintingServer);

        restPrintingServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, printingServerDTO.getPrintingServerId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(printingServerDTO))
            )
            .andExpect(status().isOk());

        // Validate the PrintingServer in the database
        List<PrintingServer> printingServerList = printingServerRepository.findAll();
        assertThat(printingServerList).hasSize(databaseSizeBeforeUpdate);
        PrintingServer testPrintingServer = printingServerList.get(printingServerList.size() - 1);
        assertThat(testPrintingServer.getPrintingServerName()).isEqualTo(UPDATED_PRINTING_SERVER_NAME);
        assertThat(testPrintingServer.getPrintingServerDescription()).isEqualTo(UPDATED_PRINTING_SERVER_DESCRIPTION);
        assertThat(testPrintingServer.getPrintingServerHost()).isEqualTo(UPDATED_PRINTING_SERVER_HOST);
        assertThat(testPrintingServer.getPrintingServerPort()).isEqualTo(UPDATED_PRINTING_SERVER_PORT);
        assertThat(testPrintingServer.getPrintingServerDns()).isEqualTo(UPDATED_PRINTING_SERVER_DNS);
        assertThat(testPrintingServer.getPrintingServerProxy()).isEqualTo(UPDATED_PRINTING_SERVER_PROXY);
        assertThat(testPrintingServer.getPrintingServerParam1()).isEqualTo(UPDATED_PRINTING_SERVER_PARAM_1);
        assertThat(testPrintingServer.getPrintingServerParam2()).isEqualTo(UPDATED_PRINTING_SERVER_PARAM_2);
        assertThat(testPrintingServer.getPrintingServerParam3()).isEqualTo(UPDATED_PRINTING_SERVER_PARAM_3);
        assertThat(testPrintingServer.getPrintingServerStat()).isEqualTo(UPDATED_PRINTING_SERVER_STAT);
        assertThat(testPrintingServer.getPrintingServerParams()).isEqualTo(UPDATED_PRINTING_SERVER_PARAMS);
        assertThat(testPrintingServer.getPrintingServerAttributs()).isEqualTo(UPDATED_PRINTING_SERVER_ATTRIBUTS);
    }

    @Test
    @Transactional
    void putNonExistingPrintingServer() throws Exception {
        int databaseSizeBeforeUpdate = printingServerRepository.findAll().size();
        printingServer.setPrintingServerId(count.incrementAndGet());

        // Create the PrintingServer
        PrintingServerDTO printingServerDTO = printingServerMapper.toDto(printingServer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrintingServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, printingServerDTO.getPrintingServerId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(printingServerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingServer in the database
        List<PrintingServer> printingServerList = printingServerRepository.findAll();
        assertThat(printingServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrintingServer() throws Exception {
        int databaseSizeBeforeUpdate = printingServerRepository.findAll().size();
        printingServer.setPrintingServerId(count.incrementAndGet());

        // Create the PrintingServer
        PrintingServerDTO printingServerDTO = printingServerMapper.toDto(printingServer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrintingServerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(printingServerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingServer in the database
        List<PrintingServer> printingServerList = printingServerRepository.findAll();
        assertThat(printingServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrintingServer() throws Exception {
        int databaseSizeBeforeUpdate = printingServerRepository.findAll().size();
        printingServer.setPrintingServerId(count.incrementAndGet());

        // Create the PrintingServer
        PrintingServerDTO printingServerDTO = printingServerMapper.toDto(printingServer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrintingServerMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(printingServerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrintingServer in the database
        List<PrintingServer> printingServerList = printingServerRepository.findAll();
        assertThat(printingServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrintingServerWithPatch() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        int databaseSizeBeforeUpdate = printingServerRepository.findAll().size();

        // Update the printingServer using partial update
        PrintingServer partialUpdatedPrintingServer = new PrintingServer();
        partialUpdatedPrintingServer.setPrintingServerId(printingServer.getPrintingServerId());

        partialUpdatedPrintingServer
            .printingServerHost(UPDATED_PRINTING_SERVER_HOST)
            .printingServerPort(UPDATED_PRINTING_SERVER_PORT)
            .printingServerProxy(UPDATED_PRINTING_SERVER_PROXY)
            .printingServerParam2(UPDATED_PRINTING_SERVER_PARAM_2)
            .printingServerParam3(UPDATED_PRINTING_SERVER_PARAM_3)
            .printingServerParams(UPDATED_PRINTING_SERVER_PARAMS)
            .printingServerAttributs(UPDATED_PRINTING_SERVER_ATTRIBUTS);

        restPrintingServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrintingServer.getPrintingServerId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrintingServer))
            )
            .andExpect(status().isOk());

        // Validate the PrintingServer in the database
        List<PrintingServer> printingServerList = printingServerRepository.findAll();
        assertThat(printingServerList).hasSize(databaseSizeBeforeUpdate);
        PrintingServer testPrintingServer = printingServerList.get(printingServerList.size() - 1);
        assertThat(testPrintingServer.getPrintingServerName()).isEqualTo(DEFAULT_PRINTING_SERVER_NAME);
        assertThat(testPrintingServer.getPrintingServerDescription()).isEqualTo(DEFAULT_PRINTING_SERVER_DESCRIPTION);
        assertThat(testPrintingServer.getPrintingServerHost()).isEqualTo(UPDATED_PRINTING_SERVER_HOST);
        assertThat(testPrintingServer.getPrintingServerPort()).isEqualTo(UPDATED_PRINTING_SERVER_PORT);
        assertThat(testPrintingServer.getPrintingServerDns()).isEqualTo(DEFAULT_PRINTING_SERVER_DNS);
        assertThat(testPrintingServer.getPrintingServerProxy()).isEqualTo(UPDATED_PRINTING_SERVER_PROXY);
        assertThat(testPrintingServer.getPrintingServerParam1()).isEqualTo(DEFAULT_PRINTING_SERVER_PARAM_1);
        assertThat(testPrintingServer.getPrintingServerParam2()).isEqualTo(UPDATED_PRINTING_SERVER_PARAM_2);
        assertThat(testPrintingServer.getPrintingServerParam3()).isEqualTo(UPDATED_PRINTING_SERVER_PARAM_3);
        assertThat(testPrintingServer.getPrintingServerStat()).isEqualTo(DEFAULT_PRINTING_SERVER_STAT);
        assertThat(testPrintingServer.getPrintingServerParams()).isEqualTo(UPDATED_PRINTING_SERVER_PARAMS);
        assertThat(testPrintingServer.getPrintingServerAttributs()).isEqualTo(UPDATED_PRINTING_SERVER_ATTRIBUTS);
    }

    @Test
    @Transactional
    void fullUpdatePrintingServerWithPatch() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        int databaseSizeBeforeUpdate = printingServerRepository.findAll().size();

        // Update the printingServer using partial update
        PrintingServer partialUpdatedPrintingServer = new PrintingServer();
        partialUpdatedPrintingServer.setPrintingServerId(printingServer.getPrintingServerId());

        partialUpdatedPrintingServer
            .printingServerName(UPDATED_PRINTING_SERVER_NAME)
            .printingServerDescription(UPDATED_PRINTING_SERVER_DESCRIPTION)
            .printingServerHost(UPDATED_PRINTING_SERVER_HOST)
            .printingServerPort(UPDATED_PRINTING_SERVER_PORT)
            .printingServerDns(UPDATED_PRINTING_SERVER_DNS)
            .printingServerProxy(UPDATED_PRINTING_SERVER_PROXY)
            .printingServerParam1(UPDATED_PRINTING_SERVER_PARAM_1)
            .printingServerParam2(UPDATED_PRINTING_SERVER_PARAM_2)
            .printingServerParam3(UPDATED_PRINTING_SERVER_PARAM_3)
            .printingServerStat(UPDATED_PRINTING_SERVER_STAT)
            .printingServerParams(UPDATED_PRINTING_SERVER_PARAMS)
            .printingServerAttributs(UPDATED_PRINTING_SERVER_ATTRIBUTS);

        restPrintingServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrintingServer.getPrintingServerId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrintingServer))
            )
            .andExpect(status().isOk());

        // Validate the PrintingServer in the database
        List<PrintingServer> printingServerList = printingServerRepository.findAll();
        assertThat(printingServerList).hasSize(databaseSizeBeforeUpdate);
        PrintingServer testPrintingServer = printingServerList.get(printingServerList.size() - 1);
        assertThat(testPrintingServer.getPrintingServerName()).isEqualTo(UPDATED_PRINTING_SERVER_NAME);
        assertThat(testPrintingServer.getPrintingServerDescription()).isEqualTo(UPDATED_PRINTING_SERVER_DESCRIPTION);
        assertThat(testPrintingServer.getPrintingServerHost()).isEqualTo(UPDATED_PRINTING_SERVER_HOST);
        assertThat(testPrintingServer.getPrintingServerPort()).isEqualTo(UPDATED_PRINTING_SERVER_PORT);
        assertThat(testPrintingServer.getPrintingServerDns()).isEqualTo(UPDATED_PRINTING_SERVER_DNS);
        assertThat(testPrintingServer.getPrintingServerProxy()).isEqualTo(UPDATED_PRINTING_SERVER_PROXY);
        assertThat(testPrintingServer.getPrintingServerParam1()).isEqualTo(UPDATED_PRINTING_SERVER_PARAM_1);
        assertThat(testPrintingServer.getPrintingServerParam2()).isEqualTo(UPDATED_PRINTING_SERVER_PARAM_2);
        assertThat(testPrintingServer.getPrintingServerParam3()).isEqualTo(UPDATED_PRINTING_SERVER_PARAM_3);
        assertThat(testPrintingServer.getPrintingServerStat()).isEqualTo(UPDATED_PRINTING_SERVER_STAT);
        assertThat(testPrintingServer.getPrintingServerParams()).isEqualTo(UPDATED_PRINTING_SERVER_PARAMS);
        assertThat(testPrintingServer.getPrintingServerAttributs()).isEqualTo(UPDATED_PRINTING_SERVER_ATTRIBUTS);
    }

    @Test
    @Transactional
    void patchNonExistingPrintingServer() throws Exception {
        int databaseSizeBeforeUpdate = printingServerRepository.findAll().size();
        printingServer.setPrintingServerId(count.incrementAndGet());

        // Create the PrintingServer
        PrintingServerDTO printingServerDTO = printingServerMapper.toDto(printingServer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrintingServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, printingServerDTO.getPrintingServerId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(printingServerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingServer in the database
        List<PrintingServer> printingServerList = printingServerRepository.findAll();
        assertThat(printingServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrintingServer() throws Exception {
        int databaseSizeBeforeUpdate = printingServerRepository.findAll().size();
        printingServer.setPrintingServerId(count.incrementAndGet());

        // Create the PrintingServer
        PrintingServerDTO printingServerDTO = printingServerMapper.toDto(printingServer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrintingServerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(printingServerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrintingServer in the database
        List<PrintingServer> printingServerList = printingServerRepository.findAll();
        assertThat(printingServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrintingServer() throws Exception {
        int databaseSizeBeforeUpdate = printingServerRepository.findAll().size();
        printingServer.setPrintingServerId(count.incrementAndGet());

        // Create the PrintingServer
        PrintingServerDTO printingServerDTO = printingServerMapper.toDto(printingServer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrintingServerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(printingServerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrintingServer in the database
        List<PrintingServer> printingServerList = printingServerRepository.findAll();
        assertThat(printingServerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrintingServer() throws Exception {
        // Initialize the database
        printingServerRepository.saveAndFlush(printingServer);

        int databaseSizeBeforeDelete = printingServerRepository.findAll().size();

        // Delete the printingServer
        restPrintingServerMockMvc
            .perform(delete(ENTITY_API_URL_ID, printingServer.getPrintingServerId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrintingServer> printingServerList = printingServerRepository.findAll();
        assertThat(printingServerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
