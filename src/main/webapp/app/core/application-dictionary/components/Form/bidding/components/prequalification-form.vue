<template>
    <div class="prequalification-form">
        <el-form ref="mainForm"  label-position="left" label-width="200px" :model="formData" size="mini">
            <el-row>
                <el-col :span="8">
                    <el-form-item label="Prequalification Method">
                        <el-select v-model="formData.method" class="form-input" clearable filterable>
                            <el-option v-for="item in methods" :key="item.id" :label="item.name" :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row v-for="(criteria, index) in formData.criteria" :key="criteria.id" class="criteria-section" :class="`criteria-${index}`">
                <el-col :span="24">
                    <el-row>
                        <el-col :span="8">
                            <el-form-item label="Criteria">
                                <el-input v-model="criteria.name" clearable></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row v-for="(subCriteria, subIndex) in criteria.subCriteria" :key="subCriteria.id" class="sub-criteria-section" :class="`sub-${subIndex}`">
                        <el-col :span="24">
                            <el-row>
                                <el-col :span="8">
                                    <el-form-item label="Sub Criteria">
                                        <el-input v-model="subCriteria.name" clearable></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-table border class="question-list" :data="subCriteria.questions" highlight-current-row size="mini">
                                <el-table-column label="No." width="50">
                                    <template slot-scope="{ $index }">
                                        {{ $index + 1 }}
                                    </template>
                                </el-table-column>

                                <el-table-column label="Question" min-width="320" show-overflow-tooltip sortable prop="question"></el-table-column>

                                <el-table-column label="Requirement" width="150">
                                    <template slot-scope="{ row }">
                                        <el-select v-model="row.requirement" clearable size="mini">
                                            <el-option v-for="item in requirements" :key="item.code" :label="item.name" :value="item.code"></el-option>
                                        </el-select>
                                    </template>
                                </el-table-column>

                            </el-table>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </el-form>

    </div>
</template>
<script lang="ts" src="./prequalification-form.component.ts"></script>
<style lang="scss">
    .compact .prequalification-form {
        .el-table--mini {

            td,
            th {
                height: 35px;
            }
        }
    }

</style>
<style lang="scss" scoped>
    .prequalification-form {
        .criteria-section {

            &:not(.criteria-0),
            >.el-col>.sub-criteria-section:not(.sub-0) {
                margin-top: 10px;
            }
        }
    }

</style>
