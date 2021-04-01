<template>
    <div class="app-container">



        <el-row v-if="index" class="main" ref="tableWrapper">
            <el-col :span="24">


                <el-col :span="24">
                    <el-table :data="tableData" :default-sort="{prop: 'date', order: 'descending'}" size="mini" style="width: 100%">
                        <el-table-column min-width="30" label="No">
                            <template slot-scope="row">
                                {{ row.$index+1 }}
                            </template>
                        </el-table-column>
                        <el-table-column prop="2" label="Criteria" sortable min-width="190">
                        </el-table-column>
                        <el-table-column prop="3" label="Add Sub Criteria type" sortable width="180">
                            <el-button size="mini" icon="el-icon-plus" type="primary" @click="dialogTableVisible = true"></el-button>
                        </el-table-column>
                        <el-table-column prop="4" label="Edit" sortable width="180">
                            <el-button size="mini" icon="el-icon-edit" type="primary" @click="dialogTableVisible"></el-button>
                        </el-table-column>
                        <el-table-column align="center" min-width="20">
                            <template slot="header">
                                <el-button size="mini" icon="el-icon-plus" type="primary" @click="addNewRow"></el-button>
                            </template>
                            <template slot-scope="row">
                                <el-button size="mini" icon="el-icon-delete" type="danger" @click="removeBusinessCategory(row.$index)"></el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-col>

            </el-col>
        </el-row>
        <template>
            <el-dialog title="Add Sub Criteria Type" :visible.sync="dialogTableVisible">
                <el-form ref="biddingInformation" label-position="left" label-width="150px" :model="bidding" :rules="rules" size="mini">
                    <el-row :gutter="24">
                        <el-col>
                            <el-form-item label="Criteria" prop="name" required>
                                <el-input v-model="dummy.data" class="form-input" clearable></el-input>
                            </el-form-item>
                            <el-form-item label="Evaluation Type" prop="biddingNo">
                                <!-- <template>
                                    <el-select v-model="value" placeholder="Select">
                                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                                        </el-option>
                                    </el-select>
                                </template> -->
                                <el-switch v-model="isHidden" active-text="Score" inactive-text="Pass File">
                                </el-switch>
                            </el-form-item>
                            <el-form-item v-if="isHidden"  label="Multipline Option" prop="name">
                                <el-switch v-model="isHidden1">
                                </el-switch>                               
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
                <el-table v-if="isHidden"  size="mini" :data="tableData2">
                    <el-table-column v-if="isHidden1" min-width="30" label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>
                    <el-table-column v-if="isHidden1" property="2" label="Criteria" min-width="180"></el-table-column>
                    <el-table-column v-if="isHidden1" property="3"  label="Nilai" min-width="180"></el-table-column>
                    <el-table-column v-if="isHidden1" align="center" min-width="30">
                        <template slot="header">
                            <el-button size="mini" icon="el-icon-plus" type="primary" @click="addNewRow"></el-button>
                        </template>
                        <template slot-scope="row">
                            <el-button size="mini" icon="el-icon-delete" type="danger" @click="removeBusinessCategory(row.$index)"></el-button>
                        </template>
                    </el-table-column>
                </el-table>
                 <el-table v-if="!isHidden" size="mini" :data="tableData1">
                    <el-table-column min-width="30" label="No">
                        <template slot-scope="row">
                            {{ row.$index+1 }}
                        </template>
                    </el-table-column>
                    <el-table-column property="2" label="Criteria" min-width="180"></el-table-column>                
                    <el-table-column align="center" min-width="30">
                        <template slot="header">
                            <el-button size="mini" icon="el-icon-plus" type="primary" @click="addNewRow"></el-button>
                        </template>
                        <template slot-scope="row">
                            <el-button size="mini" icon="el-icon-delete" type="danger" @click="removeBusinessCategory(row.$index)"></el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-dialog>
        </template>

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
