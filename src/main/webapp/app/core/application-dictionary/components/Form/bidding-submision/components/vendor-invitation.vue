<template>
    <div class="app-container">

        <el-divider content-position="left"><h4>Vendor Business Category</h4></el-divider>

        <el-row :gutter="24">
            <el-col :span="20">
                <el-table
                    v-loading="processing"
                    ref="vendorInvitation"
                    highlight-current-row
                    border stripe
                    size="mini"
                    style="width: 100%; height: 100%"
                  
                   
                    :data="eventschedule">

                    <el-table-column
                        min-width="30"
                        label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>

                    <el-table-column
                        min-width="100"
                        prop="event"
                        label="Event"/>

                    <el-table-column
                        min-width="100"
                        prop="start"
                        label="Start date"/>

                    <el-table-column
                        min-width="100"
                        prop="end"
                        label="End Date"/>


                     <el-table-column
                            min-width="60"
                            sortable
                            label="Bidding Schedule">
                            <template slot-scope="{ row }">
                                <el-button
                                    class="button"
                                    icon="el el-download-alt"
                                    size="mini"
                                    type="primary"
                                    @click="view(row)">
                                    PDF

                                    
                                </el-button>
                            </template>
                        </el-table-column>

                  

                </el-table>
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts" src="./vendor-invitation.component.ts"></script>
