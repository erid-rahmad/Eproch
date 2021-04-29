<template>
    <div class="app-container">

        <div v-if="page===1">
            <el-row>
                <el-col :span="24">
                    <el-table :data="announcmentGridData" style="width: 80%" size="mini">
                        <el-table-column width="50" label="No">
                            <template slot-scope="row">
                                {{ row.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column min-width="80" sortable prop="biddingDocNo" label="Bidding No" />
                        <el-table-column min-width="100" sortable prop="biddingName" label="Title" />
                        <el-table-column align="center" min-width="20">
                            <template slot="header">
                                <el-button size="mini" icon="el-icon-plus" type="primary" @click="view(row)" />
                            </template>
                            <template slot-scope="{row}">
                                <el-button size="mini" icon="el-icon-view" type="primary" @click="moreInfo(row)" />
                            </template>
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
        </div>
        <div v-if="page===2">
            <DetailsAnnouncementForm @back="back" :moreinfo="moreinfo" ></DetailsAnnouncementForm>
        </div>
        <div v-if="page===3">
            <AddAnnouncementForm @back="back" ></AddAnnouncementForm>         
        </div>
        <el-dialog title="Joined Vendors" :visible.sync="vendorListVisible">
            <el-table border :data="joinedVendors" size="mini">
                <el-table-column width="50" label="No">
                    <template slot-scope="row">
                        {{ row.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column property="vendorName" label="Vendor Name" width="200" show-overflow-tooltip></el-table-column>
                <el-table-column property="address" label="Address" min-width="200" show-overflow-tooltip></el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>

<script lang="ts" src="./event-announcement.component.ts">
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
