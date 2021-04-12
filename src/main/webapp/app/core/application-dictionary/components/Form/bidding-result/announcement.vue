<template>
    <div class="app-container bidding-result-announcement">
        <div class="toolbar">
            <el-button v-if="!index" icon="el-icon-close" size="mini" type="danger" @click="closeDetail">
                Close
            </el-button>
           
            <el-button v-if="!index" icon="el-icon-check" size="mini"  style="margin-left: 0" type="primary" @click="openVendorConfirmation">
                Vendor Confirmation
            </el-button>
        </div>
        <!-- <el-button v-if="!index" icon="el-icon-check" size="mini" style="margin-left: 0" type="primary" @click="openVendorConfirmation">
            Vendor Confirmation ww
        </el-button> -->
        <el-table v-if="index" ref="mainGrid" border :data="announcements" highlight-current-row size="mini" stripe style="width: 100%" @current-change="onCurrentRowChanged">
            <el-table-column label="No" width="50">
                <template slot-scope="{ $index }">
                    {{ $index + 1 }}
                </template>
            </el-table-column>
            <el-table-column label="Bidding No." min-width="100" prop="biddingNo" sortable></el-table-column>
            <el-table-column label="Bidding Type" min-width="150" prop="biddingType" show-overflow-tooltip sortable></el-table-column>
            <el-table-column label="Bidding Title" min-width="150" prop="biddingTitle" show-overflow-tooltip sortable></el-table-column>
            <el-table-column label="Type" min-width="100" prop="type" sortable></el-table-column>
            <el-table-column label="Winner" min-width="150" prop="winner" show-overflow-tooltip sortable></el-table-column>
            <!-- <el-table-column label="Status" min-width="150" prop="status" show-overflow-tooltip sortable></el-table-column> -->
            <el-table-column min-width="100">
                <template slot="header">
                    &nbsp;
                </template>
                <template slot-scope="{ row }">
                    <el-button icon="el-icon-edit" size="mini" type="primary" @click="viewDetail(row)">
                        View
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        
        
        <bidding-result-announcement-detail v-else :data="selectedRow"></bidding-result-announcement-detail>
         
        
    </div> 


</template>
<script lang="ts" src="./announcement.component.ts"></script>

<style lang="scss" scoped>
    .bidding-result-announcement {
        display: grid;
        grid-template-columns: 100%;
        grid-template-rows: 36px auto;

        .toolbar {
            padding: 4px;
        }

    }

    .compact .el-table tbody .el-button {
        margin: 5px 0;
    }

</style>
