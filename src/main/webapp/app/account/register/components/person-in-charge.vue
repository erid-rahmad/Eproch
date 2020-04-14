<template>
    <div>
        <h4>{{ $t('register.pic.contact.title') }}</h4>
        <div class="table-container">
            <div class="toolbar float-top-right">
                <el-button
                    type="primary"
                    icon="el-icon-plus"
                    circle
                    @click.native.prevent="addPerson('contact')"
                />
            </div>
            <el-table
                ref="contacts"
                max-height="250"
                style="width: 100%"
                :data="contacts"
            >
                <el-table-column
                    fixed
                    prop="name"
                    min-width="128"
                    :label="$t('register.pic.name')"
                />
                <el-table-column
                    prop="position"
                    min-width="128"
                    :label="$t('register.pic.position')"
                />
                <el-table-column
                    prop="phone"
                    min-width="128"
                    :label="$t('register.pic.phone')"
                />
                <el-table-column
                    prop="email"
                    min-width="256"
                    :label="$t('register.pic.email')"
                />
                <el-table-column
                    prop="subcategory"
                    min-width="128"
                    :label="$t('register.pic.subcategory')"
                />
                <el-table-column
                    prop="username"
                    min-width="128"
                    :label="$t('register.pic.username')"
                />
                <el-table-column
                    fixed="right"
                    label="Operations"
                    width="200"
                >
                    <template slot-scope="scope">
                        <el-button
                            @click="edit(scope.row)"
                            type="primary"
                            size="mini"
                            icon="el-icon-edit"
                            plain
                            :title="$t('entity.action.edit')"
                        />
                        <el-button
                            @click="prepareRemove(scope.row)"
                            type="danger"
                            size="mini"
                            icon="el-icon-delete"
                            plain
                            :title="$t('entity.action.delete')"
                        />
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <h4>{{ $t('register.pic.functionary.title') }}</h4>
        <div class="table-container">
            <div class="toolbar float-top-right">
                <el-button
                    type="primary"
                    icon="el-icon-plus"
                    circle
                    @click.native.prevent="addPerson('functionary')"
                />
            </div>
            <p v-if="hasErrors" :text="errors.functionaries.message" class="error">Your company must have at least a functionary.</p>
            <el-table
                ref="functionaries"
                max-height="250"
                style="width: 100%"
                :data="functionaries"
            >
                <el-table-column
                    fixed
                    prop="name"
                    :label="$t('register.pic.name')"
                />
                <el-table-column
                    prop="position"
                    :label="$t('register.pic.position')"
                />
                <el-table-column
                    prop="phone"
                    :label="$t('register.pic.phone')"
                />
                <el-table-column
                    prop="email"
                    :label="$t('register.pic.email')"
                />
                <el-table-column
                    fixed="right"
                    label="Operations"
                    width="200"
                >
                    <template slot-scope="scope">
                        <el-button
                            @click="edit(scope.row)"
                            type="primary"
                            size="mini"
                            icon="el-icon-edit"
                            plain
                            :title="$t('entity.action.edit')"
                        />
                        <el-button
                            @click="prepareRemove(scope.row)"
                            type="danger"
                            size="mini"
                            icon="el-icon-delete"
                            plain
                            :title="$t('entity.action.delete')"
                        />
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <el-dialog
            :title="$t('register.form.contact[\'title.edit\']')"
            :visible.sync="editDialogVisible"
        >
            <el-row :gutter="16">
                <el-col :span="18" :offset="3">
                    <person-in-charge-update
                        ref="dialogBody"
                        :event-bus="eventBus"
                        :contact="editingForm === 'contact'"
                    />
                </el-col>
            </el-row>
            <span slot="footer" class="dialog-footer">
                <el-button @click="hideDialog">Cancel</el-button>
                <el-button :loading="loading" type="primary" @click="saveDocument">Save</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script lang="ts" src="./person-in-charge.component.ts"></script>

<style lang="scss" scoped>
.error {
    background: none;
    color: #ff4949;
    font-size: 12px;
    line-height: 1;
}
</style>
