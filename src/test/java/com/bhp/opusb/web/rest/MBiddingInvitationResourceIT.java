package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MBiddingInvitation;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CAnnouncement;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.MBiddingInvitationRepository;
import com.bhp.opusb.service.MBiddingInvitationService;
import com.bhp.opusb.service.dto.MBiddingInvitationDTO;
import com.bhp.opusb.service.mapper.MBiddingInvitationMapper;
import com.bhp.opusb.service.dto.MBiddingInvitationCriteria;
import com.bhp.opusb.service.MBiddingInvitationQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.bhp.opusb.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MBiddingInvitationResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MBiddingInvitationResourceIT {

    private static final String DEFAULT_INVITATION_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_INVITATION_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ANSWER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ANSWER_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ANSWER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MBiddingInvitationRepository mBiddingInvitationRepository;

    @Autowired
    private MBiddingInvitationMapper mBiddingInvitationMapper;

    @Autowired
    private MBiddingInvitationService mBiddingInvitationService;

    @Autowired
    private MBiddingInvitationQueryService mBiddingInvitationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMBiddingInvitationMockMvc;

    private MBiddingInvitation mBiddingInvitation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingInvitation createEntity(EntityManager em) {
        MBiddingInvitation mBiddingInvitation = new MBiddingInvitation()
            .invitationStatus(DEFAULT_INVITATION_STATUS)
            .reason(DEFAULT_REASON)
            .answerDate(DEFAULT_ANSWER_DATE)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        return mBiddingInvitation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MBiddingInvitation createUpdatedEntity(EntityManager em) {
        MBiddingInvitation mBiddingInvitation = new MBiddingInvitation()
            .invitationStatus(UPDATED_INVITATION_STATUS)
            .reason(UPDATED_REASON)
            .answerDate(UPDATED_ANSWER_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        return mBiddingInvitation;
    }

    @BeforeEach
    public void initTest() {
        mBiddingInvitation = createEntity(em);
    }

    @Test
    @Transactional
    public void createMBiddingInvitation() throws Exception {
        int databaseSizeBeforeCreate = mBiddingInvitationRepository.findAll().size();

        // Create the MBiddingInvitation
        MBiddingInvitationDTO mBiddingInvitationDTO = mBiddingInvitationMapper.toDto(mBiddingInvitation);
        restMBiddingInvitationMockMvc.perform(post("/api/m-bidding-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingInvitationDTO)))
            .andExpect(status().isCreated());

        // Validate the MBiddingInvitation in the database
        List<MBiddingInvitation> mBiddingInvitationList = mBiddingInvitationRepository.findAll();
        assertThat(mBiddingInvitationList).hasSize(databaseSizeBeforeCreate + 1);
        MBiddingInvitation testMBiddingInvitation = mBiddingInvitationList.get(mBiddingInvitationList.size() - 1);
        assertThat(testMBiddingInvitation.getInvitationStatus()).isEqualTo(DEFAULT_INVITATION_STATUS);
        assertThat(testMBiddingInvitation.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testMBiddingInvitation.getAnswerDate()).isEqualTo(DEFAULT_ANSWER_DATE);
        assertThat(testMBiddingInvitation.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMBiddingInvitation.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMBiddingInvitationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mBiddingInvitationRepository.findAll().size();

        // Create the MBiddingInvitation with an existing ID
        mBiddingInvitation.setId(1L);
        MBiddingInvitationDTO mBiddingInvitationDTO = mBiddingInvitationMapper.toDto(mBiddingInvitation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMBiddingInvitationMockMvc.perform(post("/api/m-bidding-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingInvitationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingInvitation in the database
        List<MBiddingInvitation> mBiddingInvitationList = mBiddingInvitationRepository.findAll();
        assertThat(mBiddingInvitationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMBiddingInvitations() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList
        restMBiddingInvitationMockMvc.perform(get("/api/m-bidding-invitations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingInvitation.getId().intValue())))
            .andExpect(jsonPath("$.[*].invitationStatus").value(hasItem(DEFAULT_INVITATION_STATUS)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].answerDate").value(hasItem(sameInstant(DEFAULT_ANSWER_DATE))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMBiddingInvitation() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get the mBiddingInvitation
        restMBiddingInvitationMockMvc.perform(get("/api/m-bidding-invitations/{id}", mBiddingInvitation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mBiddingInvitation.getId().intValue()))
            .andExpect(jsonPath("$.invitationStatus").value(DEFAULT_INVITATION_STATUS))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.answerDate").value(sameInstant(DEFAULT_ANSWER_DATE)))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMBiddingInvitationsByIdFiltering() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        Long id = mBiddingInvitation.getId();

        defaultMBiddingInvitationShouldBeFound("id.equals=" + id);
        defaultMBiddingInvitationShouldNotBeFound("id.notEquals=" + id);

        defaultMBiddingInvitationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMBiddingInvitationShouldNotBeFound("id.greaterThan=" + id);

        defaultMBiddingInvitationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMBiddingInvitationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMBiddingInvitationsByInvitationStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where invitationStatus equals to DEFAULT_INVITATION_STATUS
        defaultMBiddingInvitationShouldBeFound("invitationStatus.equals=" + DEFAULT_INVITATION_STATUS);

        // Get all the mBiddingInvitationList where invitationStatus equals to UPDATED_INVITATION_STATUS
        defaultMBiddingInvitationShouldNotBeFound("invitationStatus.equals=" + UPDATED_INVITATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByInvitationStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where invitationStatus not equals to DEFAULT_INVITATION_STATUS
        defaultMBiddingInvitationShouldNotBeFound("invitationStatus.notEquals=" + DEFAULT_INVITATION_STATUS);

        // Get all the mBiddingInvitationList where invitationStatus not equals to UPDATED_INVITATION_STATUS
        defaultMBiddingInvitationShouldBeFound("invitationStatus.notEquals=" + UPDATED_INVITATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByInvitationStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where invitationStatus in DEFAULT_INVITATION_STATUS or UPDATED_INVITATION_STATUS
        defaultMBiddingInvitationShouldBeFound("invitationStatus.in=" + DEFAULT_INVITATION_STATUS + "," + UPDATED_INVITATION_STATUS);

        // Get all the mBiddingInvitationList where invitationStatus equals to UPDATED_INVITATION_STATUS
        defaultMBiddingInvitationShouldNotBeFound("invitationStatus.in=" + UPDATED_INVITATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByInvitationStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where invitationStatus is not null
        defaultMBiddingInvitationShouldBeFound("invitationStatus.specified=true");

        // Get all the mBiddingInvitationList where invitationStatus is null
        defaultMBiddingInvitationShouldNotBeFound("invitationStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingInvitationsByInvitationStatusContainsSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where invitationStatus contains DEFAULT_INVITATION_STATUS
        defaultMBiddingInvitationShouldBeFound("invitationStatus.contains=" + DEFAULT_INVITATION_STATUS);

        // Get all the mBiddingInvitationList where invitationStatus contains UPDATED_INVITATION_STATUS
        defaultMBiddingInvitationShouldNotBeFound("invitationStatus.contains=" + UPDATED_INVITATION_STATUS);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByInvitationStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where invitationStatus does not contain DEFAULT_INVITATION_STATUS
        defaultMBiddingInvitationShouldNotBeFound("invitationStatus.doesNotContain=" + DEFAULT_INVITATION_STATUS);

        // Get all the mBiddingInvitationList where invitationStatus does not contain UPDATED_INVITATION_STATUS
        defaultMBiddingInvitationShouldBeFound("invitationStatus.doesNotContain=" + UPDATED_INVITATION_STATUS);
    }


    @Test
    @Transactional
    public void getAllMBiddingInvitationsByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where reason equals to DEFAULT_REASON
        defaultMBiddingInvitationShouldBeFound("reason.equals=" + DEFAULT_REASON);

        // Get all the mBiddingInvitationList where reason equals to UPDATED_REASON
        defaultMBiddingInvitationShouldNotBeFound("reason.equals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where reason not equals to DEFAULT_REASON
        defaultMBiddingInvitationShouldNotBeFound("reason.notEquals=" + DEFAULT_REASON);

        // Get all the mBiddingInvitationList where reason not equals to UPDATED_REASON
        defaultMBiddingInvitationShouldBeFound("reason.notEquals=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByReasonIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where reason in DEFAULT_REASON or UPDATED_REASON
        defaultMBiddingInvitationShouldBeFound("reason.in=" + DEFAULT_REASON + "," + UPDATED_REASON);

        // Get all the mBiddingInvitationList where reason equals to UPDATED_REASON
        defaultMBiddingInvitationShouldNotBeFound("reason.in=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where reason is not null
        defaultMBiddingInvitationShouldBeFound("reason.specified=true");

        // Get all the mBiddingInvitationList where reason is null
        defaultMBiddingInvitationShouldNotBeFound("reason.specified=false");
    }
                @Test
    @Transactional
    public void getAllMBiddingInvitationsByReasonContainsSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where reason contains DEFAULT_REASON
        defaultMBiddingInvitationShouldBeFound("reason.contains=" + DEFAULT_REASON);

        // Get all the mBiddingInvitationList where reason contains UPDATED_REASON
        defaultMBiddingInvitationShouldNotBeFound("reason.contains=" + UPDATED_REASON);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByReasonNotContainsSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where reason does not contain DEFAULT_REASON
        defaultMBiddingInvitationShouldNotBeFound("reason.doesNotContain=" + DEFAULT_REASON);

        // Get all the mBiddingInvitationList where reason does not contain UPDATED_REASON
        defaultMBiddingInvitationShouldBeFound("reason.doesNotContain=" + UPDATED_REASON);
    }


    @Test
    @Transactional
    public void getAllMBiddingInvitationsByAnswerDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where answerDate equals to DEFAULT_ANSWER_DATE
        defaultMBiddingInvitationShouldBeFound("answerDate.equals=" + DEFAULT_ANSWER_DATE);

        // Get all the mBiddingInvitationList where answerDate equals to UPDATED_ANSWER_DATE
        defaultMBiddingInvitationShouldNotBeFound("answerDate.equals=" + UPDATED_ANSWER_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByAnswerDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where answerDate not equals to DEFAULT_ANSWER_DATE
        defaultMBiddingInvitationShouldNotBeFound("answerDate.notEquals=" + DEFAULT_ANSWER_DATE);

        // Get all the mBiddingInvitationList where answerDate not equals to UPDATED_ANSWER_DATE
        defaultMBiddingInvitationShouldBeFound("answerDate.notEquals=" + UPDATED_ANSWER_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByAnswerDateIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where answerDate in DEFAULT_ANSWER_DATE or UPDATED_ANSWER_DATE
        defaultMBiddingInvitationShouldBeFound("answerDate.in=" + DEFAULT_ANSWER_DATE + "," + UPDATED_ANSWER_DATE);

        // Get all the mBiddingInvitationList where answerDate equals to UPDATED_ANSWER_DATE
        defaultMBiddingInvitationShouldNotBeFound("answerDate.in=" + UPDATED_ANSWER_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByAnswerDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where answerDate is not null
        defaultMBiddingInvitationShouldBeFound("answerDate.specified=true");

        // Get all the mBiddingInvitationList where answerDate is null
        defaultMBiddingInvitationShouldNotBeFound("answerDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByAnswerDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where answerDate is greater than or equal to DEFAULT_ANSWER_DATE
        defaultMBiddingInvitationShouldBeFound("answerDate.greaterThanOrEqual=" + DEFAULT_ANSWER_DATE);

        // Get all the mBiddingInvitationList where answerDate is greater than or equal to UPDATED_ANSWER_DATE
        defaultMBiddingInvitationShouldNotBeFound("answerDate.greaterThanOrEqual=" + UPDATED_ANSWER_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByAnswerDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where answerDate is less than or equal to DEFAULT_ANSWER_DATE
        defaultMBiddingInvitationShouldBeFound("answerDate.lessThanOrEqual=" + DEFAULT_ANSWER_DATE);

        // Get all the mBiddingInvitationList where answerDate is less than or equal to SMALLER_ANSWER_DATE
        defaultMBiddingInvitationShouldNotBeFound("answerDate.lessThanOrEqual=" + SMALLER_ANSWER_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByAnswerDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where answerDate is less than DEFAULT_ANSWER_DATE
        defaultMBiddingInvitationShouldNotBeFound("answerDate.lessThan=" + DEFAULT_ANSWER_DATE);

        // Get all the mBiddingInvitationList where answerDate is less than UPDATED_ANSWER_DATE
        defaultMBiddingInvitationShouldBeFound("answerDate.lessThan=" + UPDATED_ANSWER_DATE);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByAnswerDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where answerDate is greater than DEFAULT_ANSWER_DATE
        defaultMBiddingInvitationShouldNotBeFound("answerDate.greaterThan=" + DEFAULT_ANSWER_DATE);

        // Get all the mBiddingInvitationList where answerDate is greater than SMALLER_ANSWER_DATE
        defaultMBiddingInvitationShouldBeFound("answerDate.greaterThan=" + SMALLER_ANSWER_DATE);
    }


    @Test
    @Transactional
    public void getAllMBiddingInvitationsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where uid equals to DEFAULT_UID
        defaultMBiddingInvitationShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mBiddingInvitationList where uid equals to UPDATED_UID
        defaultMBiddingInvitationShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where uid not equals to DEFAULT_UID
        defaultMBiddingInvitationShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mBiddingInvitationList where uid not equals to UPDATED_UID
        defaultMBiddingInvitationShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where uid in DEFAULT_UID or UPDATED_UID
        defaultMBiddingInvitationShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mBiddingInvitationList where uid equals to UPDATED_UID
        defaultMBiddingInvitationShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where uid is not null
        defaultMBiddingInvitationShouldBeFound("uid.specified=true");

        // Get all the mBiddingInvitationList where uid is null
        defaultMBiddingInvitationShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where active equals to DEFAULT_ACTIVE
        defaultMBiddingInvitationShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingInvitationList where active equals to UPDATED_ACTIVE
        defaultMBiddingInvitationShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where active not equals to DEFAULT_ACTIVE
        defaultMBiddingInvitationShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mBiddingInvitationList where active not equals to UPDATED_ACTIVE
        defaultMBiddingInvitationShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMBiddingInvitationShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mBiddingInvitationList where active equals to UPDATED_ACTIVE
        defaultMBiddingInvitationShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        // Get all the mBiddingInvitationList where active is not null
        defaultMBiddingInvitationShouldBeFound("active.specified=true");

        // Get all the mBiddingInvitationList where active is null
        defaultMBiddingInvitationShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMBiddingInvitationsByAdOrganizationIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);
        ADOrganization adOrganization = ADOrganizationResourceIT.createEntity(em);
        em.persist(adOrganization);
        em.flush();
        mBiddingInvitation.setAdOrganization(adOrganization);
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mBiddingInvitationList where adOrganization equals to adOrganizationId
        defaultMBiddingInvitationShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mBiddingInvitationList where adOrganization equals to adOrganizationId + 1
        defaultMBiddingInvitationShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingInvitationsByAnnouncementIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);
        CAnnouncement announcement = CAnnouncementResourceIT.createEntity(em);
        em.persist(announcement);
        em.flush();
        mBiddingInvitation.setAnnouncement(announcement);
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);
        Long announcementId = announcement.getId();

        // Get all the mBiddingInvitationList where announcement equals to announcementId
        defaultMBiddingInvitationShouldBeFound("announcementId.equals=" + announcementId);

        // Get all the mBiddingInvitationList where announcement equals to announcementId + 1
        defaultMBiddingInvitationShouldNotBeFound("announcementId.equals=" + (announcementId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingInvitationsByBiddingIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);
        MBidding bidding = MBiddingResourceIT.createEntity(em);
        em.persist(bidding);
        em.flush();
        mBiddingInvitation.setBidding(bidding);
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);
        Long biddingId = bidding.getId();

        // Get all the mBiddingInvitationList where bidding equals to biddingId
        defaultMBiddingInvitationShouldBeFound("biddingId.equals=" + biddingId);

        // Get all the mBiddingInvitationList where bidding equals to biddingId + 1
        defaultMBiddingInvitationShouldNotBeFound("biddingId.equals=" + (biddingId + 1));
    }


    @Test
    @Transactional
    public void getAllMBiddingInvitationsByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);
        CVendor vendor = CVendorResourceIT.createEntity(em);
        em.persist(vendor);
        em.flush();
        mBiddingInvitation.setVendor(vendor);
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);
        Long vendorId = vendor.getId();

        // Get all the mBiddingInvitationList where vendor equals to vendorId
        defaultMBiddingInvitationShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mBiddingInvitationList where vendor equals to vendorId + 1
        defaultMBiddingInvitationShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMBiddingInvitationShouldBeFound(String filter) throws Exception {
        restMBiddingInvitationMockMvc.perform(get("/api/m-bidding-invitations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mBiddingInvitation.getId().intValue())))
            .andExpect(jsonPath("$.[*].invitationStatus").value(hasItem(DEFAULT_INVITATION_STATUS)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].answerDate").value(hasItem(sameInstant(DEFAULT_ANSWER_DATE))))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMBiddingInvitationMockMvc.perform(get("/api/m-bidding-invitations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMBiddingInvitationShouldNotBeFound(String filter) throws Exception {
        restMBiddingInvitationMockMvc.perform(get("/api/m-bidding-invitations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMBiddingInvitationMockMvc.perform(get("/api/m-bidding-invitations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMBiddingInvitation() throws Exception {
        // Get the mBiddingInvitation
        restMBiddingInvitationMockMvc.perform(get("/api/m-bidding-invitations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMBiddingInvitation() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        int databaseSizeBeforeUpdate = mBiddingInvitationRepository.findAll().size();

        // Update the mBiddingInvitation
        MBiddingInvitation updatedMBiddingInvitation = mBiddingInvitationRepository.findById(mBiddingInvitation.getId()).get();
        // Disconnect from session so that the updates on updatedMBiddingInvitation are not directly saved in db
        em.detach(updatedMBiddingInvitation);
        updatedMBiddingInvitation
            .invitationStatus(UPDATED_INVITATION_STATUS)
            .reason(UPDATED_REASON)
            .answerDate(UPDATED_ANSWER_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MBiddingInvitationDTO mBiddingInvitationDTO = mBiddingInvitationMapper.toDto(updatedMBiddingInvitation);

        restMBiddingInvitationMockMvc.perform(put("/api/m-bidding-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingInvitationDTO)))
            .andExpect(status().isOk());

        // Validate the MBiddingInvitation in the database
        List<MBiddingInvitation> mBiddingInvitationList = mBiddingInvitationRepository.findAll();
        assertThat(mBiddingInvitationList).hasSize(databaseSizeBeforeUpdate);
        MBiddingInvitation testMBiddingInvitation = mBiddingInvitationList.get(mBiddingInvitationList.size() - 1);
        assertThat(testMBiddingInvitation.getInvitationStatus()).isEqualTo(UPDATED_INVITATION_STATUS);
        assertThat(testMBiddingInvitation.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testMBiddingInvitation.getAnswerDate()).isEqualTo(UPDATED_ANSWER_DATE);
        assertThat(testMBiddingInvitation.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMBiddingInvitation.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMBiddingInvitation() throws Exception {
        int databaseSizeBeforeUpdate = mBiddingInvitationRepository.findAll().size();

        // Create the MBiddingInvitation
        MBiddingInvitationDTO mBiddingInvitationDTO = mBiddingInvitationMapper.toDto(mBiddingInvitation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMBiddingInvitationMockMvc.perform(put("/api/m-bidding-invitations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mBiddingInvitationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MBiddingInvitation in the database
        List<MBiddingInvitation> mBiddingInvitationList = mBiddingInvitationRepository.findAll();
        assertThat(mBiddingInvitationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMBiddingInvitation() throws Exception {
        // Initialize the database
        mBiddingInvitationRepository.saveAndFlush(mBiddingInvitation);

        int databaseSizeBeforeDelete = mBiddingInvitationRepository.findAll().size();

        // Delete the mBiddingInvitation
        restMBiddingInvitationMockMvc.perform(delete("/api/m-bidding-invitations/{id}", mBiddingInvitation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MBiddingInvitation> mBiddingInvitationList = mBiddingInvitationRepository.findAll();
        assertThat(mBiddingInvitationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
