<template>
    <div class="app-container">
        <el-row ref="tableWrapper" class="main">
            <el-col :span="24">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <keep-alive>
                        <el-col :span="24">
                            <el-table :data="biddingInvitationsGridData" size="mini" style="width: 100%">
                                <el-table-column label="No" min-width="50">
                                    <template slot-scope="row">
                                        {{ row.$index + 1 }}
                                    </template>
                                </el-table-column>
                                <el-table-column label="Publish Date" min-width="210" sortable>
                                    <template slot-scope="{ row }">
                                        <span>{{ row.announcementPublishDate | formatDate }}</span>
                                    </template>
                                </el-table-column>
                                <el-table-column label="Bidding Name" min-width="210" prop="biddingName"
                                                 sortable></el-table-column>
                                <el-table-column label="End Date" min-width="210"  sortable>
                                    <template slot-scope="{ row }">
                                        <span>{{ row.announcementEndDate | formatDate }}</span>
                                    </template>
                                </el-table-column>
                                <el-table-column label="Status" min-width="150" prop="invitationStatus" sortable>
                                    <template slot-scope="{ row }">
                                        <span>{{ getStatus(row) }}</span>
                                    </template>
                                </el-table-column>
                                <el-table-column label="Detail" prop="4" sortable width="180">
                                    <template slot-scope="{ row }">
                                        <el-button class="btn-attachment" icon="el-icon-search" size="mini"
                                                   type="primary" @click="viewemail(row)"></el-button>
                                    </template>
                                </el-table-column>
                                <el-table-column  label="Action" prop="6" sortable width="180">
                                    <template v-if="answerbutton" slot-scope="{ row }">
                                        <el-button class="btn-attachment" icon="el-icon-circle-check" size="mini"
                                                   type="primary" @click="minat(row)">
                                        </el-button>
                                        <el-button class="btn-attachment" icon="el-icon-circle-close" size="mini"
                                                   type="primary" @click="tidakminat(row)">
                                        </el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </el-col>
                    </keep-alive>
                </el-tabs>
            </el-col>
        </el-row>

        <el-dialog :visible.sync="reasonPA" center title="" width="80%">
            <span>Reason For Not Interested </span>
            <el-input v-model="reason" class="form-input" clearable></el-input>
            <span slot="footer" class="dialog-footer">
        <el-button @click="reasonPA = false">Cancel</el-button>
        <el-button type="primary" @click="tidakminatAction">Confirm</el-button>
      </span>
        </el-dialog>
        <el-dialog :visible.sync="acceptPA" title="" width="40%">
            <div class="header">
                <h1>{{ selecrow.biddingName }}</h1>
                <p>Start Date {{ selecrow.announcementPublishDate | formatDate }} </p>
                <p>End Date {{ selecrow.announcementEndDate | formatDate }} </p>
            </div>
            <el-divider content-position="center">
                <h4>Project Information</h4>
            </el-divider>
            <el-row>
                <el-col>
                    <el-table
                        ref="projectInformations"
                        :data="bidding.projectInformations"
                        border
                        highlight-current-row
                        size="mini"
                        stripe
                        style="height: 100%"
                    >
                        <el-table-column label="No" min-width="30">
                            <template slot-scope="{ $index }">
                                {{ $index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column label="Information" min-width="100" prop="name"></el-table-column>
                        <el-table-column label="Attachment" min-width="150">
                            <template slot-scope="{ row }">
                                <el-button
                                    :title="printFileName(row.attachment)"
                                    class="btn-attachment"
                                    icon="el-icon-download"
                                    size="mini"
                                    type="primary"
                                    @click="downloadAttachment(row)"
                                >
                                    {{ printFileName(row.attachment) }}
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
            <span slot="footer" class="dialog-footer">
        <el-button v-if="accbutton" type="primary" @click="minatAction">Accept Invitation</el-button>
      </span>
        </el-dialog>
        <el-dialog :visible.sync="showemaildetail" center width="60%">
            <div v-html="info.announcementDescription"></div>
        </el-dialog>
    </div>
</template>

<script lang="ts" src="./bidding-registration.component.ts"></script>

<style lang="scss">
.el-tabs__header {
    margin: 0px;
}

.el-table__fixed {
    box-shadow: none;
}

.main {
    padding: 0px;
}

.form-input {
    width: 100%;
}

.header {
    text-align: center;
    font-size: 15px;
}
</style>
