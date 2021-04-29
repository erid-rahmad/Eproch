<template>
    <div class="app-container">

        <el-row v-if="index" class="main" ref="tableWrapper">
            <el-col :span="24">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <keep-alive>
                        <el-col :span="24">
                            <el-table :data="biddingInvitationsGridData" size="mini"  style="width: 100%">
                                <el-table-column min-width="50" label="No">
                                    <template slot-scope="row">
                                        {{ row.$index+1 }}
                                    </template>
                                </el-table-column>
                                <!-- <el-table-column prop="publishDate" label="Tanggal" sortable width="100">
                                </el-table-column> -->
                                <el-table-column prop="biddingName" label="Nama Pengadaan" sortable min-width="210">
                                </el-table-column>
                                <el-table-column  label="Publish Date" sortable min-width="210">
                                    
                                        <template slot-scope="{ row }">
                                            <span>{{formattime(row.announcementPublishDate) }}</span>
                                        </template>
                                   
                                    
                                </el-table-column>
                                <el-table-column prop="invitationStatus" label="Status" sortable min-width="150">
                                </el-table-column>              
                                <el-table-column prop="6" label="Detail" sortable min-width="180">
                                    <template slot-scope="{ row }">
                                        <el-button class="btn-attachment" icon="el-icon-search" size="mini" type="primary" @click="downloadAttachment(row)">
                                           {{row.re}}
                                        </el-button>
                                    </template>
                                </el-table-column>
   
                                <el-table-column min-width="60" sortable label="Minat">
                                    <template slot-scope="{ row }">
                                        <el-button class="button" icon="el el-download-alt" size="mini" type="primary" @click="onClick('add')">
                                            View
                                        </el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </el-col>
                    </keep-alive>
                </el-tabs>
            </el-col>
        </el-row>

        <el-row v-if="!index" class="main" ref="tableWrapper">
            <el-col :span="24" class="tab-container">
                <product-information :setRowProductCatalog="setRow" @closeProductInformation="closeProductInformation" />
            </el-col>
        </el-row>
        <el-row v-if="index" class="header">
        </el-row>
    </div>
</template>

<script lang="ts" src="./bidding-announcement.component.ts">
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
