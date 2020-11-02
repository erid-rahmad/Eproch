<template>
    <div>
        <el-divider content-position="left"><h4>{{ $t('register.pic.contact.title') }}</h4></el-divider>
        <p>
            <el-alert
                :title="$t('register.pic.contact.description')"
                :type="errors.type.contacts"
                :closable="false"
                show-icon>
            </el-alert>
        </p>
        <div class="table-container">
            <div class="toolbar float-top-right">
                <el-button
                    type="primary"
                    icon="el-icon-plus"
                    @click.native.prevent="addPerson('contacts')">
                    {{ $t('entity.action.add') }}
                </el-button>
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
                    min-width="128"
                    :label="$t('register.pic.email')"
                />
                <el-table-column
                    min-width="256"
                    :label="$t('register.pic.subcategory')"
                >
                    <template slot-scope="{row}">
                        <span>{{ printBusinessCategory(row) }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="userLogin"
                    min-width="128"
                    :label="$t('register.pic.username')"
                />
                <el-table-column
                    fixed="right"
                    align="center"
                    :label="$t('register.form.operation')"
                    width="128">
                    <template slot-scope="{row, $index}">
                        <el-button
                            @click="edit(row, 'contacts', $index)"
                            type="primary"
                            size="mini"
                            icon="el-icon-edit"
                            plain
                            :title="$t('entity.action.edit')"
                        />
                        <el-button
                            v-if="$index !== 0"
                            @click="prepareRemove('contacts', $index)"
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

        <el-divider content-position="left"><h4>{{ $t('register.pic.functionary.title') }}</h4></el-divider>
        <p>
            <el-alert
                :title="$t('register.pic.functionary.description')"
                :type="errors.type.functionaries"
                :closable="false"
                show-icon>
            </el-alert>
        </p>
        <div class="table-container">
            <div class="toolbar float-top-right">
                <el-button
                    type="primary"
                    icon="el-icon-plus"
                    @click.native.prevent="addPerson('functionaries')">
                    {{ $t('entity.action.add') }}
                </el-button>
            </div>
            <el-table
                ref="functionaries"
                max-height="250"
                style="width: 100%"
                :data="functionaries"
            >
                <el-table-column
                    fixed
                    min-width="128"
                    prop="name"
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
                    min-width="128"
                    :label="$t('register.pic.email')"
                />
                <el-table-column
                    fixed="right"
                    align="center"
                    :label="$t('register.form.operation')"
                    width="128">
                    <template slot-scope="scope">
                        <el-button
                            @click="edit(scope.row, 'functionaries', scope.$index)"
                            type="primary"
                            size="mini"
                            icon="el-icon-edit"
                            plain
                            :title="$t('entity.action.edit')"
                        />
                        <el-button
                            @click="prepareRemove('functionaries', scope.$index)"
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
            @closed="hideDialog">
            <el-row :gutter="16">
                <el-col :span="24" :offset="0">
                    <person-in-charge-update
                        ref="dialogBody"
                        :user="user"
                        :event-bus="eventBus"
                        :contact="editingForm === 'contacts'"
                    />
                </el-col>
            </el-row>
            <span slot="footer" class="dialog-footer">
                <el-button @click="hideDialog" icon="el-icon-close">{{ $t('entity.action.cancel') }}</el-button>
                <el-button :loading="loading" type="primary" @click="saveDocument" icon="el-icon-check">{{ $t('entity.action.save') }}</el-button>
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
