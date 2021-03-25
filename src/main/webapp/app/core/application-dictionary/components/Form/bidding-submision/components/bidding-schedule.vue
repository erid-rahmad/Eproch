<template>
    <div class="app-container">

        <el-divider content-position="left">
            <h4>Event Schedule</h4>
        </el-divider>

        <el-row :gutter="24">
            <el-col :span="20">
                <el-table v-loading="processing" ref="biddingSchedule" highlight-current-row border stripe size="mini" style="width: 100%; height: 100%" :data="projectinformation">

                    <el-table-column min-width="30" label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>

                    <el-table-column min-width="100" prop="name" label="Information" />

                    <el-table-column min-width="100" prop="attachmentName" label="atachment">
                        <template slot-scope="{ row }">
                            <el-button class="btn-attachment" icon="el-icon-download" size="mini" type="primary" @click="downloadAttachment(row)">
                                {{ row.attachmentName }}
                                
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts" src="./bidding-schedule.component.ts"></script>
