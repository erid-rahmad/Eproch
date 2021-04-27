<template>
    <div class="app-container">

        <div v-if="page===1">
            <el-row>
                <el-col :span="24">
                    <el-table :data="gridData1" style="width: 100%" size="mini">
                        <el-table-column width="50" label="No">
                            <template slot-scope="row">
                                {{ row.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column min-width="80" sortable prop="documentNo" label="Bidding No" />
                        <el-table-column min-width="100" sortable prop="name" label="Title" />
                         <el-table-column min-width="100" sortable prop="biddingTypeName" label="Bidding Type" />
                        <el-table-column min-width="100" label="Bidding Schedule">
                            <template slot-scope="{ row }">
                                <el-button class="button" icon="el-icon-search" size="mini" type="primary" @click="ScheduleListVisible=true">
                                    View
                                </el-button>
                            </template>
                        </el-table-column>
                         <el-table-column min-width="70" sortable prop="documentStatus" label="Bidding Status" />
                          <el-table-column min-width="60" sortable prop="join" label="Joined Vendor" />
                        <el-table-column align="center" min-width="30">
   
                            <template slot-scope="row">
                                <!-- <el-button size="mini" icon="el-icon-view" type="primary" @click="moreinfo()" /> -->
                                <el-button size="mini"  type="primary" @click="moreinfo()" >Action</el-button>
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
             <EmailGrid  @back="back" ></EmailGrid>
        </div>
        <el-dialog title="Bidding Schedule" :visible.sync="ScheduleListVisible" width="90%">
            <el-table border :data="BiddingSchedule" size="mini">
                <el-table-column width="60" label="No">
                    <template slot-scope="row">
                        {{ row.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column property="event" label="Event" width="200" show-overflow-tooltip></el-table-column>
                <el-table-column property="startdate" label="Start Date" min-width="200" show-overflow-tooltip></el-table-column>

                <el-table-column property="finisdate" label="Finish Date" min-width="200" show-overflow-tooltip></el-table-column>
                <el-table-column min-width="100" label="Action">
                    <template slot-scope="{ row }">
                        <el-button class="button" icon="el-icon-caret-right" size="mini" type="primary" @click="view(row)">
                            Action
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>

<script lang="ts" src="./evaluation-announcement.component.ts">
</script>

<style lang="scss">
    .el-table__fixed,
    .el-table__fixed-right {
        box-shadow: none;
    }

    .main {
        padding: 0px;

        .button {
            width: 100%;
        }
    }

    .header {
        .button {
            margin-bottom: 5px;
        }
    }

    .form-input {
        width: 100%;
    }

    .steps-control-btn-group {
        float: right;
        padding: 0 24px;
    }

    .ProseMirror {
        >*+* {
            margin-top: 0.75em;
        }

        ul,
        ol {
            padding: 0 1rem;
        }

        h1,
        h2,
        h3,
        h4,
        h5,
        h6 {
            line-height: 1.1;
        }

        code {
            background-color: rgba(#616161, 0.1);
            color: #616161;
        }

        pre {
            background: #0D0D0D;
            color: #FFF;
            font-family: 'JetBrainsMono', monospace;
            padding: 0.75rem 1rem;
            border-radius: 0.5rem;

            code {
                color: inherit;
                padding: 0;
                background: none;
                font-size: 0.8rem;
            }
        }

        img {
            max-width: 100%;
            height: auto;
        }

        blockquote {
            padding-left: 1rem;
            border-left: 2px solid rgba(#0D0D0D, 0.1);
        }

        hr {
            border: none;
            border-top: 2px solid rgba(#0D0D0D, 0.1);
            margin: 2rem 0;
        }
    }

</style>
