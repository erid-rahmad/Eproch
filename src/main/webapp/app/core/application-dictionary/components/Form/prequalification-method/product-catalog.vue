<template>
    <div class="app-container">

        <el-row v-if="index" class="main" ref="tableWrapper">
            <el-col :span="24">
                <el-tabs v-model="activeName" @tab-click="handleClick" >
                    <keep-alive>
                        <el-col :span="24">
                            <el-table  :data="tableData" size="mini" :default-sort="{prop: 'date', order: 'descending'}" >
                                <el-table-column min-width="30" label="No">
                                    <template slot-scope="row">
                                        {{ row.$index+1 }}
                                    </template>
                                </el-table-column>
                                <el-table-column prop="1" label="Prequalification Method" sortable min-width="180">
                                </el-table-column>
                                <el-table-column prop="2" label="Status" sortable min-width="180">
                                </el-table-column>
                                <el-table-column prop="3" label="Type" sortable min-width="180">
                                </el-table-column>
                                <el-table-column min-width="160" sortable label="Bidding Schedule">
                                    <template slot="header">
                                        <el-button size="mini" icon="el-icon-plus" type="primary" @click="onClick('add')"></el-button>
                                    </template>
                                    <template slot-scope="row">
                                        <el-button size="mini" icon="el-icon-view" type="primary" @click="removeBusinessCategory(row.$index)"></el-button>
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
        <el-dialog title="Shipping address" :visible.sync="dialogTableVisible">
            <el-table :data="gridData">
                <el-table-column property="no" label="no" width="150"><template slot-scope="row">
                        {{ row.$index+1 }}
                    </template></el-table-column>
                <el-table-column property="name" label="Name" width="200"></el-table-column>
                <el-table-column property="address" label="Address"></el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>
<script lang="ts" src="./product-catalog.component.ts">
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
