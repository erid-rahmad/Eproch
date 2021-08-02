<template>
    <div class="app-container card-view">
        <div class="toolbar" v-if="acceptPA">
            <el-button
                icon="el-icon-close"
                size="mini"
                type="danger"
                @click="closeDetail"
            >
                Close
            </el-button>
        </div>
        <div class="card">
            <el-row ref="tableWrapper" v-if="!acceptPA">
                <el-col :span="24">
                    <el-tabs >
                        <keep-alive>
                            <el-col :span="24">
                                <el-table :data="preqRegistGridData"
                                        size="mini"
                                        style="width: 100%"
                                        v-loading="loading"
                                        border
                                >
                                    <el-table-column label="No" align="center" min-width="30">
                                        <template slot-scope="row">
                                            {{ row.$index + 1 }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="Publish Date" align="center" min-width="150" sortable>
                                        <template slot-scope="{ row }">
                                            <span>{{ row.announcementPublishDate | formatDate }}</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="Prequalification Name" align="center" min-width="210"
                                            sortable>
                                        <template slot-scope="{ row }">
                                            <span>{{ row.name || row.prequalificationName }}</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="Type" min-width="180" align="center" sortable>
                                        <template slot-scope="{ row }">
                                            <span>{{ row.prequalificationType||row.type == 'O' ? 'Announcement' : 'Invitation' }}</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="End Date" min-width="180" align="center" v-if="isVendor" sortable>
                                        <template slot-scope="{ row }">
                                            <span>{{ row.announcementEndDate | formatDate }}</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="Status" align="center" min-width="100" sortable v-if="isVendor">
                                        <template slot-scope="{ row }">
                                            <span>{{ getStatus(row) }}</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="Joined Vendor" align="center" min-width="100" 
                                        sortable v-if="!isVendor" prop="joinedVendor"/>
                                    <el-table-column label="Detail" v-if="isVendor" align="center"  sortable width="80">
                                        <template slot-scope="{ row }">
                                            <el-button class="btn-attachment" icon="el-icon-search" size="mini"
                                                    type="primary" @click="viewemail(row)"></el-button>
                                        </template>
                                    </el-table-column>
                                    <el-table-column  label="Action" align="center" sortable width="130">
                                        <template v-if="isVendor" slot-scope="{ row }">
                                            <el-button class="btn-attachment" icon="el-icon-circle-check" size="mini"
                                                    type="primary" @click="detail(row)">
                                            </el-button>
                                            <el-button class="btn-attachment" icon="el-icon-circle-close" size="mini"
                                                    type="primary" @click="tidakminat(row)">
                                            </el-button>
                                        </template>
                                        <template v-else slot-scope="{ row }">
                                            <el-button class="btn-attachment" icon="el-icon-circle-check" size="mini"
                                                    type="primary" @click="detail(row)">
                                                    Evaluate
                                            </el-button>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </el-col>
                        </keep-alive>
                    </el-tabs>
                </el-col>
            </el-row>
            <regist-detail v-if="acceptPA && isVendor" :data="selecrow" />
            <regist-detail-buyer v-if="acceptPA && !isVendor" :data="selecrow" />
        </div>
        <el-dialog :visible.sync="reasonPA" center title="" width="80%">
            <span>Reason For Not Interested </span>
            <el-input v-model="reason" class="form-input" clearable></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="reasonPA = false">Cancel</el-button>
                <el-button type="primary" @click="tidakminatAction">Confirm</el-button>
            </span>
        </el-dialog>
        <el-dialog :visible.sync="showemaildetail" center width="60%" >
            <div v-loading="loadingEmailDetail" v-html="info.announcementDescription"></div>
        </el-dialog>
    </div>
</template>

<script lang="ts" src="./prequalification-registration.component.ts"></script>

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
