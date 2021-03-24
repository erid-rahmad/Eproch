<template>
    <div class="app-container">
   
        <el-row v-if="index" class="main" ref="tableWrapper">
            <el-col :span="24">

                <el-tabs v-model="activeName" @tab-click="handleClick">

                   

                        <keep-alive>

                            <el-col :span="24">
                                <el-table :data="tableData" :default-sort="{prop: 'date', order: 'descending'}" style="width: 100%">
                                    <el-table-column min-width="30" label="No">
                                        <template slot-scope="row">
                                            {{ row.$index+1 }}
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="1" label="Cost Evaluation" sortable width="180">
                                    </el-table-column>
                                    <el-table-column prop="2" label="Biding No" sortable width="180">
                                    </el-table-column>
                                    <el-table-column prop="3" label="Bidding title" sortable width="180">
                                    </el-table-column>
                                    <el-table-column prop="4" label="Bidding type" sortable width="180">
                                    </el-table-column>
                                    <el-table-column prop="5" label="Aproval Status" sortable width="180">
                                       
                                    </el-table-column>
                                    <el-table-column prop="6" label="Modified Date" sortable width="180">
                                    </el-table-column>
                                    <el-table-column prop="7" label="Modified By" width="180">
                                    </el-table-column>
                                    <el-table-column min-width="60" sortable label="Bidding Schedule">
                                        <template slot-scope="{ row }">
                                            <el-button class="button" icon="el el-download-alt" size="mini" type="primary" @click="onClick('add')">
                                                Action
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
