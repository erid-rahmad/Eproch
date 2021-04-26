<template>
    <div class="app-container">

        <div v-if="page===1">       
                <el-button type="danger" plain size="mini" icon="el-icon-close" @click="back">
                    Back
                </el-button>
                <el-button type="primary" size="mini" style="margin-left: 0px" v-loading.fullscreen.lock="fullscreenLoading" @click="dialogTableVisible11 = true">
                    Submit <em class="el-icon-arrow-right"></em>
                </el-button>
                <el-divider content-position="left">
                    <h4>Bidding Submission</h4>
                </el-divider>
        
            <el-form ref="biddingInformation" label-position="left" label-width="200px" size="mini">
                <el-row :gutter="24">
                    <el-col :span="12">
                        <el-form-item label="Bidding No" prop="documentNo">
                            <h7>{{moreInformation.documentNo}}</h7>
                        </el-form-item>
                        <el-form-item label="Title" prop="title">
                            <h7>{{moreInformation.name}}</h7>
                        </el-form-item>
                        <el-form-item label="Biding Type" prop="bidingType">
                            <h7>{{moreInformation.biddingTypeName}}</h7>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="Submision Start Date" prop="SubmisionDeadline">
                            <h7>04/15/2021 14:00</h7>
                        </el-form-item>
                        <el-form-item label="Submision End Date" prop="SubmisionDeadline">
                            <h7>04/20/2021 14:00</h7>
                        </el-form-item>
                        <el-form-item label="Remaining Time Deadline" prop="SubmisionDeadline">
                            <h7>5 Days 00 Hours 00 Minute</h7>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <el-row>
            </el-row>
            <div>
                <div class="tombolaction">
                    <el-button class="button" type="primary" @click="goadministrasi(row)">
                        Administration Propossal
                    </el-button>
                </div>
                <div class="tombolaction">
                    <el-button class="button" type="primary" @click="gotechnical(row)">
                        Technical Proposal
                    </el-button>
                </div>
                <div class="tombolaction">
                    <el-button class="button" type="primary" @click="goprice(row)">
                        Price Proposal
                    </el-button>
                </div>
            </div>

        </div>
        <div v-if="page===2">
            <AdministrationProposal @back="back"></AdministrationProposal>
        </div>
        <div v-if="page===3">
            <TechnicalProposal @back="back"></TechnicalProposal>
        </div>
        <div v-if="page===4">
            <Priceproposal @back="back"></Priceproposal>
        </div>
    </div>
</template>

<script lang="ts" src="./bidding-submission.component.ts"></script>
<style>
    .tombolaction {
        /* width: 150px;
        padding: 10px;

        margin: auto; */
        display: block;
        width: 100%;
        border: none;

        padding: 14px 28px;
        font-size: 16px;
        cursor: pointer;
        text-align: center;

    }

</style>
