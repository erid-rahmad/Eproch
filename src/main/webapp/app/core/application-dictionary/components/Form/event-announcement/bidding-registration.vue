<template>
    <div class="app-container">
        <el-row class="main" ref="tableWrapper">
            <el-col :span="24">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <keep-alive>
                        <el-col :span="24">
                            <el-table :data="biddingInvitationsGridData" size="mini" style="width: 100%">
                                <el-table-column min-width="50" label="No">
                                    <template slot-scope="row">
                                        {{ row.$index+1 }}
                                    </template>
                                </el-table-column>
                                <!-- <el-table-column prop="announcementPublishDate" label="Publish Date" sortable min-width="210"></el-table-column> -->

                                <el-table-column  label="Publish Date" sortable min-width="210">
                                    <template slot-scope="{ row }">
                                        <span>{{formattime(row.announcementPublishDate) }}</span>                                       
                                    </template>
                                </el-table-column>

                                <el-table-column prop="biddingName" label="Nama Pengadaan" sortable min-width="210">
                                </el-table-column>
                                <el-table-column prop="2" label="Tanggal Selesai" sortable min-width="210">
                                </el-table-column>
                                <el-table-column prop="invitationStatus" label="Status" sortable min-width="150">
                                </el-table-column>

                                <el-table-column prop="4" label="Detail" sortable width="180">
                                    <template slot-scope="{ row }">
                                        <el-button class="btn-attachment" icon="el-icon-search" size="mini" type="primary" @click="viewemail(row)">
                                        </el-button>
                                    </template>
                                </el-table-column>

                                <el-table-column prop="6" label="Minat" sortable width="180">
                                    <template slot-scope="{ row }">
                                        <el-button class="btn-attachment" icon="el-icon-circle-check" size="mini" type="primary" @click="minat(row)">
                                        </el-button>
                                        <el-button class="btn-attachment" icon="el-icon-circle-close" size="mini" type="primary" @click="tidakminat(row)">
                                        </el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </el-col>
                    </keep-alive>
                </el-tabs>
            </el-col>
        </el-row>

  

        <el-dialog title="" :visible.sync="reasonPA" width="60%" center>
            <span>Alasan Tidak Tertarik</span>
            <el-input v-model="reason" class="form-input" clearable></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="reasonPA = false">Cancel</el-button>
                <el-button type="primary" @click="tidakminatAction">Confirm</el-button>
            </span>
        </el-dialog>
        <el-dialog :visible.sync="showemaildetail" width="60%" center>
            <div v-html="info.announcementDescription"></div>
        </el-dialog>
    </div>
</template>

<script lang="ts" src="./bidding-registration.component.ts">
</script>

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

</style>
