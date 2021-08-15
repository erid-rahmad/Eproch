<template>
    <div class="app-container card-view bidding-process">


        <div v-if="page===1">
            <el-row class="card">
                <el-col :span="24">
                    <el-table :data="prequalGrid"
                              style="width: 100%"
                              v-loading="loading"
                              border
                              size="mini">
                        <el-table-column width="50" align="center"  label="No">
                            <template slot-scope="row">
                                {{ row.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column min-width="50" sortable prop="documentNo" label="Prequalification No" />
                        <el-table-column min-width="100" sortable prop="name" label="Title" />
                        <el-table-column min-width="80" sortable align="center" label="Prequalification Type">
                            <template slot-scope="{ row }">
                                {{ row.type == 'O' ? 'Announcement' : 'Invitation' }}
                            </template>
                        </el-table-column>
                        <el-table-column min-width="50" label="Prequalification Schedule">
                            <template slot-scope="{ row }">
                                <el-button class="button" size="mini" style="width: 100%" @click="viewSchedule(row)">
                                    <svg-icon name="icomoo/084-calendar"></svg-icon> View Schedule
                                </el-button>
                            </template>
                        </el-table-column>
                         <el-table-column min-width="70" sortable align="center"  label="Prequalification Status" >
                             <template slot-scope="{ row }">
                                 {{ formatBiddingStatus(row.status) }}
                             </template>
                         </el-table-column>
<!--                          <el-table-column min-width="60" sortable prop="join" label="Joined Vendor" />-->
                        <el-table-column align="center" min-width="30">
                            <template slot-scope="{row}">
                                <!-- <el-button size="mini" icon="el-icon-view" type="primary" @click="moreinfo()" /> -->
                                <el-button size="mini"  type="primary" @click="view(row)" >Action</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
        </div>
        <div v-if="page===2">
        </div>
        <div v-if="page===3">
            <!-- <AddAnnouncementForm @back="back"></AddAnnouncementForm> -->
             <EmailGrid  @back="back" :pickRow="pickRow" ></EmailGrid>
        </div>
    </div>
</template>

<script lang="ts" src="./evaluation-announcement.component.ts">
</script>

<style lang="scss">
.compact .bidding-process {
    display: grid;
    grid-template-columns: 100%;
    grid-template-rows: 36px auto;

    .joined-vendor-dialog .el-table.vendor-list {
        td {
            height: 35px;
        }
    }
}

.el-table__fixed, .el-table__fixed-right{
    box-shadow: none;
}

.main {
    padding: 0px;

    .button {
        width: 100%;
    }
}

.toolbar {
    padding: 4px 16px;
}

.form-input {
    width: 100%;
}

</style>
