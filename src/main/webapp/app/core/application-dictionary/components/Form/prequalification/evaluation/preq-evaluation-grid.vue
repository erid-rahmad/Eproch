<template>
    <div class="app-container card-view">
        <!--
        <div class="toolbar">
            <el-button v-if="!index" icon="el-icon-close" size="mini" type="danger" @click="close" >
                Close
            </el-button>
            <el-button v-if="!index"  size="mini" type="primary" >
                <svg-icon name="icomoo/273-checkmark"></svg-icon>
                Save
            </el-button>
        </div>
        -->
        <div v-if="index==0" class="card">
            <el-table
                ref="mainGrid"
                v-loading="processing"
                :data="gridData"
                border
                highlight-current-row
                size="mini"
                stripe
            >
                <el-table-column align="center" fixed="right" width="200" label="Actions">
                    <template slot-scope="{ row }">
                        <el-button :underline="false" icon="el-icon-search" size="mini" title="View" type="primary"
                                   @click="view(row)"
                        >Evaluate
                        </el-button>
                        <el-button :underline="false" icon="el-icon-search" size="mini" title="View" type="primary"
                                   @click="result(row)"
                        >Result
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column width="50" label="No">
                    <template slot-scope="{ $index }">
                        {{ $index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column label="Registration Date" min-width="150" sortable>
                    <template slot-scope="{ row }">
                        {{ row.announcementPublishDate | formatDate }}
                    </template>
                </el-table-column>
                <el-table-column label="Prequalification No." min-width="150" prop="documentNo" sortable>
                </el-table-column>
                <el-table-column label="Title" min-width="140" prop="name" show-overflow-tooltip
                                 sortable>
                </el-table-column>

                <el-table-column label="Prequalification Type" min-width="130" show-overflow-tooltip sortable>
                    <template slot-scope="{ row }">
                        {{row.type=='O'?'Announcement':row.type=='C'?'Invitation':row.type}}
                    </template>           
                </el-table-column>
                <el-table-column label="Joined Vendor" align="center" min-width="100" prop="joinedVendor"/>
            </el-table>
        </div>
        <div v-if="index==1">
            <preq-evaluation :pickRow="selectedRow" @close="close"></preq-evaluation>
        </div>
        <div v-if="index==2">
            <evaluation-result @close="close" :pickRow="selectedRow"></evaluation-result>
        </div>
    </div>
</template>
<script lang="ts" src="./preq-evaluation-grid.component.ts">
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
