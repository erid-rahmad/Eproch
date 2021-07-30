<template>
    <div>

        <el-row>
            <el-col :span="8">
                <div align="right" class="grid-content bg-purple" style="padding-right: 10px"> Title

                </div>
            </el-col>
            <el-col :span="8">
                <div class="grid-content bg-purple-light">
                    <el-input
                        v-model="Title"
                        autosize
                        placeholder="Please Title"
                        size="mini"
                    >
                    </el-input>

                </div>
            </el-col>
            <el-col :span="8">
                <div class="grid-content bg-purple" style="padding-left: 10px">
                    <el-button icon="el-icon-plus" size="mini" type="primary"
                               @click="addClause"> Add clause
                    </el-button>
                    <el-button size="mini" type="primary"
                               @click="saveDocument">Save
                    </el-button>
                    <el-button size="mini" type="danger"
                               @click="close">close
                    </el-button>
                </div>
            </el-col>
        </el-row>


        <el-row
            v-for="(clause, index) in clauses"
            :key="clause.id"
            v-loading="loading"
            :class="`criteria-${index}`"
            class="criteria-section"

        >

            <el-row style="padding: 10px">
                <el-col :span="8" style="padding-right: 10px">
                    <div align="right" class="grid-content bg-purple">Select Clause</div>
                </el-col>
                <el-col :span="8">
                    <div class="grid-content bg-purple-light">
                        <ad-input-lookup
                            v-model="clause.clause"
                            :label-fields="['name']"
                            :query="['active.equals=true']"
                            placeholder="Select Clause"
                            size="mini"
                            table-name="c_clause"

                        ></ad-input-lookup>
                    </div>
                </el-col>
                <el-col :span="8" style="padding-left: 10px">
                    <div class="grid-content bg-purple">
                        <el-button size="mini" type="primary"
                                   @click="arrange(clause)">Edit
                        </el-button>
                    </div>
                </el-col>
            </el-row>

            <div v-if="clause.action" class="root">
                <SortableList v-model="clause.list" lockAxis="y">
                    <SortableItem v-for="(item, index) in clause.list" :key="index" :index="index"
                                  :item="item.clauseLine"
                    >
                    </SortableItem>
                </SortableList>
            </div>

            <el-table
                v-if="!clause.action"
                v-loading="loading"
                :data="clause.list"
                border
                size="mini"
            >
                <el-table-column
                    label="No" width="50">
                    <template v-slot="{ $index }">
                        {{ $index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column size="mini" sortable width="80">
                    <template slot-scope="{row}">
                        <el-select v-model="row.clauseLine" placeholder="Select"
                                   @focus="retrieveClauseLine(clause)">
                            <el-option
                                v-for="item in clausesOption"
                                :key="item.name"
                                :label="item.name"
                                :value="item.description"
                                size="mini">
                            </el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column label="clause Description" min-width="200" size="mini">
                    <template slot-scope="{row}">
                        <el-input
                            v-model="row.clauseLine"
                            autosize
                            placeholder="Please input"
                            type="textarea">
                        </el-input>
                    </template>
                </el-table-column>

                <el-table-column align="center" fixed="right" width="80">
                    <template slot="header">
                        <el-button icon="el-icon-plus" size="mini" type="primary"
                                   @click="addSubClause(clause)"></el-button>
                    </template>
                    <template v-slot=" row ">
                        <el-button icon="el-icon-delete" size="mini" type="danger"
                                   @click="deleteClause(row)"></el-button>
                    </template>
                </el-table-column>
            </el-table>

        </el-row>


    </div>
</template>
<script lang="ts" src="./create-clause.component.ts">

</script>

<style scoped>
</style>
