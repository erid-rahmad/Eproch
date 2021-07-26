<template>
    <div class="contract-document">

        <create-clause v-if="createClausa" @close="cancle" @save="addDocument"></create-clause>


        <el-table
            v-if="!createClausa"
            ref="documents"
            v-loading="loading"
            :data="documents"
            border
            highlight-current-row
            size="mini"
            stripe
        >
            <el-table-column
                label="No" width="50">
                <template v-slot="{ $index }">
                    {{ $index + 1 }}
                </template>
            </el-table-column>

            <el-table-column
                label="Document Name"
                min-width="200"
                prop="name"
                show-overflow-tooltip
            ></el-table-column>

            <el-table-column align="center" fixed="right" width="120">
                <template slot="header">
                    <el-button icon="el-icon-plus" size="mini" type="primary"
                               @click="createClausa=true"></el-button>
                </template>
                <template v-slot="{ row }">
                    <el-button v-if="!readOnly" icon="el-icon-delete" size="mini" type="danger"
                               @click="deleteRow(row)"></el-button>
                    <el-button v-if="!readOnly" size="mini" type="primary" @click="view(row)">Download</el-button>
                </template>
            </el-table-column>
        </el-table>


        <el-dialog
            :show-close="false"
            :visible.sync="documentFormVisible"
            title="Clause"
            width="100%"
        >
            <template>
                <el-input v-model="Title" class="form-input" clearable placeholder="Title"></el-input>
                <el-table
                    v-loading="loading"
                    :data="clauses"
                    border
                    highlight-current-row
                    size="mini"
                    stripe
                    style="width: 100%"
                >
                    <el-table-column
                        label="No" width="50">
                        <template v-slot="{ $index }">
                            {{ $index + 1 }}
                        </template>
                    </el-table-column>
                    <el-table-column label="Clause" min-width="30" size="mini">
                        <template slot-scope="{row}">
                            <ad-input-lookup
                                v-model="row.clause"
                                :label-fields="['name']"
                                :query="['active.equals=true']"
                                placeholder="Select CC"
                                size="mini"
                                table-name="c_clause"
                            ></ad-input-lookup>
                        </template>
                    </el-table-column>
                    <el-table-column  min-width="180" size="mini">
                        <template slot-scope="{row}" template>
                            <el-table
                                :data="row.list"
                                align="center"
                                stripe>
                                <el-table-column
                                    label="clause name" min-width="100" size="mini"
                                   >
                                    <template slot-scope="{row}">
                                        <el-select v-model="row.clauseLine" placeholder="Select"
                                                   @focus="retrieveClauseLine(row)">-->
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
                                                   @click="addSubClause(row)"></el-button>
                                    </template>
                                    <template v-slot=" row ">
                                        <el-button icon="el-icon-delete" size="mini" type="danger"
                                                   @click="deleteClause(row)"></el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </template>
                    </el-table-column>


                    <el-table-column align="center" fixed="right" width="80">
                        <template slot="header">
                            <el-button icon="el-icon-plus" size="mini" type="primary"
                                       @click="addClause"></el-button>
                        </template>
                        <template v-slot=" row ">
                            <el-button icon="el-icon-delete" size="mini" type="danger"
                                       @click="deleteClause(row)"></el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div slot="footer">
                    <el-button icon="el-icon-close" size="mini" style="margin-left: 0px;"
                               @click="documentFormVisible = false">
                        {{ $t('entity.action.cancel') }}
                    </el-button>
                    <el-button icon="el-icon-check" size="mini" style="margin-left: 0px;" type="primary"
                               @click="saveDocument">
                        {{ $t('entity.action.save') }}
                    </el-button>
                </div>
            </template>
        </el-dialog>
        <el-dialog
            :show-close="false"
            :visible.sync="contractTextVisible"
            title="Contract"
            width="60%"
        >
            <td v-html="contractText"></td>
            <div slot="footer">
                <el-button icon="el-icon-close" size="mini" style="margin-left: 0px;"
                           @click="contractTextVisible = false">
                    {{ $t('entity.action.cancel') }}
                </el-button>
            </div>

        </el-dialog>
    </div>
</template>
<script lang="ts" src="./contract-document.component.ts"></script>
<style lang="scss">
.contract-document {
    .btn-attachment {
        width: 100%;
    }
}

</style>
