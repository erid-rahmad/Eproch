<template>
    <div class="app-container">
        <el-row class="toolbar">
            <el-row >
                <el-col :span="24">
                    <el-button type="danger" plain size="mini" icon="el-icon-close" @click="back">
                        Back
                    </el-button>
                </el-col>
            </el-row>
        </el-row>

        <div class="card">
            <div class="header">
                <h2>{{ pickRow.biddingNo }}</h2>
                <p>{{ pickRow.biddingName }}</p>
            </div>
            <el-row :gutter="24"  >
                    <el-form label-position="left" label-min-width="210px" size="mini">
                        <el-form-item label="Tanggal Mulai">
                            {{biddingSchedule.startDate | formatDate }}
                        </el-form-item>
                        <el-form-item label="Tangaal Selesai">
                            {{biddingSchedule.endDate | formatDate}}
                        </el-form-item>
                        <div v-html ="email.announcementResultName"></div >
                        <el-form-item label="Unduh Dokumen">
                            <el-button type="primary" plain size="mini" icon="el-icon-download" @click="downloadAttachment">{{pickRow.attachmentName}}</el-button>
                        </el-form-item>
<!--                            <el-button type="primary" plain size="mini"  @click="winerTableVisible=true">Show Winer</el-button>-->

                    </el-form>
            </el-row>
        </div>
<!--        <el-dialog title="" :visible.sync="winerTableVisible">-->
<!--            <template>-->
<!--                <el-table ref="multipleTable" :data="winerTable" style="width: 100%" >-->
<!--                    <el-table-column type="selection" width="55">-->
<!--                    </el-table-column>-->
<!--                    <el-table-column property="name" label="Vendor Name" min-width="120">-->
<!--                    </el-table-column>-->
<!--                    <el-table-column property="status" label="Status" show-overflow-tooltip>-->
<!--                    </el-table-column>-->
<!--                                        <el-table-column property="email" label="Email" show-overflow-tooltip>-->
<!--                    </el-table-column>-->
<!--                </el-table>-->
<!--            </template>-->
<!--        </el-dialog>-->

    </div>
</template>
<script lang="ts" src="./details-announcement-vendor.component.ts"></script>
<style lang="scss">
    .compact
    .details-announcement {
        .el-table--mini {

            td,
            th {
                height: 35px;
            }
        }
    }
    .header {
        text-align: center;
        font-size: 15px;
        padding: 14px;
    }

</style>
<style lang="scss" scoped>
    .body {
        margin-top: 20px;
        margin-left: 20px;
    }


</style>
