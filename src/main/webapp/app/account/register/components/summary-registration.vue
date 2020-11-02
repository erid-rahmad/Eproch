<template>
    <div class='summary'>
        <el-divider content-position="left"><h4>{{ $t('register.form.summary') }}</h4></el-divider>
        
        <el-collapse :model="activeNames" accordion>
            <el-collapse-item :title="'1. '+$t('register.login.title')" name="1">

                <el-divider content-position="left"><h4>{{ $t('register.login.title') }}</h4></el-divider>
                <el-form
                    label-position="right"
                    label-width="150px"
                    size="mini"
                    :model="login"
                    >
                    <el-row :gutter="columnSpacing">
                        
                        <el-col :span="12">
                            <el-form-item :label="$t('register.login.form.username')" prop="login" required>
                                <el-input v-model="login.userLogin" class="form-input" min="3" :disabled="true" />
                            </el-form-item>
                        </el-col>

                        <el-col :span="12">
                            <el-form-item :label="$t('register.login.form.email')"  prop="email" required>
                                <el-input v-model="login.email" class="form-input" :disabled="true" />
                            </el-form-item>
                        </el-col>

                    </el-row>
                    <el-row :gutter="columnSpacing">
                        
                        <el-col :span="12">
                            <el-form-item :label="$t('register.login.form.password')"  prop="password" required>
                                <el-input v-model="login.password" class="form-input" :disabled="true" show-password />
                            </el-form-item>
                        </el-col>

                        <el-col :span="12">  
                            <el-form-item :label="$t('register.login.form.passwordConfirm')"  prop="passwordConfirmation" required>
                                <el-input v-model="login.passwordConfirmation" class="form-input" :disabled="true" show-password />
                            </el-form-item>
                        </el-col>

                        
                    </el-row>
                </el-form>

            </el-collapse-item>

            <el-collapse-item :title="'2. '+$t('register.basic.basic.title')" name="2">
                
                <el-form
                    label-position="right"
                    label-width="150px"
                    size="mini"
                    :model="company">
                    <el-divider content-position="left"><h4>{{ $t('register.basic.basic.title') }}</h4></el-divider>
                    <el-row :gutter="columnSpacing">
                        <el-col :span="12">
                            <el-form-item :label="$t('register.basic.basic.name')" prop="name" required>
                                <el-input class="form-input" v-model="company.name" :disabled="true" />
                            </el-form-item>
                            <el-form-item :label="$t('register.basic.basic.type')" prop="type" required>
                                <el-row type="flex">
                                    <el-col :span="20">
                                        <el-input class="form-input" v-model="company.type" :disabled="true" />
                                    </el-col>
                                </el-row>
                            </el-form-item>
                            <el-form-item required>
                                <el-switch
                                    style="padding-left: 20px;"
                                    v-model="company.branch"
                                    :disabled="true"
                                    :active-text="$t('register.basic.basic.branch')"
                                />
                            </el-form-item>
                        </el-col>
                        
                        <el-col :span="12">
                            <el-form-item :label="$t('register.basic.basic.npwp')" prop="npwp" required>
                                <el-input class="form-input" v-model="company.npwp" :disabled="true" />
                            </el-form-item>
                            <el-form-item :label="$t('register.basic.basic.npwpName')" prop="npwpName" required>
                                <el-input class="form-input" v-model="company.npwpName" :disabled="true" />
                            </el-form-item>
                            <el-form-item :label="$t('register.basic.basic.npwpFile')" prop="file">
                                <span v-if="company.file">{{ company.file.name }}</span>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    
                    <el-divider content-position="left"><h4>{{ $t('register.basic.address.title') }}</h4></el-divider>
                    <el-row :gutter="columnSpacing">
                        <el-col :span="12">
                            <el-form-item :label="$t('register.basic.address.address')" prop="address" required>
                                <el-input class="form-input" v-model="company.address" type="textarea" :autosize="{ maxRows: 3 }" :disabled="true"/>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item :label="$t('register.basic.address.npwpAddress')" prop="npwpAddress" required>
                                <el-input class="form-input" v-model="company.npwpAddress" type="textarea" :autosize="{ maxRows: 3 }" :disabled="true" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                    
                    <el-row :gutter="columnSpacing">
                        <el-col :span="6">
                            <el-form-item :label="$t('register.basic.address.country')" prop="country" required>
                                <el-input class="form-input" v-model="company.countryName" :disabled="true" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item :label="$t('register.basic.address.region')" prop="region" required>
                                <el-input class="form-input" v-model="company.regionName" :disabled="true" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item :label="$t('register.basic.address.npwpCountry')" prop="npwpCountry" required>
                                <el-input class="form-input" v-model="company.npwpCountryName" :disabled="true" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item :label="$t('register.basic.address.npwpRegion')" prop="npwpRegion" required>
                                <el-input class="form-input" v-model="company.npwpRegionName" :disabled="true" />
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row :gutter="columnSpacing">
                        <el-col :span="6">
                            <el-form-item :label="$t('register.basic.address.city')" prop="city" required>
                                <el-input class="form-input" v-model="company.cityName" :disabled="true" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item :label="$t('register.basic.address.postCode')" prop="postalCode">
                                <el-input class="form-input" v-model="company.postalCode" :disabled="true" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item :label="$t('register.basic.address.npwpCity')" prop="npwpCity" required>
                                <el-input class="form-input" v-model="company.npwpCityName" :disabled="true" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item :label="$t('register.basic.address.npwpPostCode')" prop="npwpPostalCode">
                                <el-input class="form-input" v-model="company.npwpPostalCode" :disabled="true" />
                            </el-form-item>
                        </el-col>
                    </el-row>

                    
                    <el-divider content-position="left"><h4>{{ $t('register.basic.contact.title') }}</h4></el-divider>
                    <el-row :gutter="columnSpacing">
                        <el-col :span="6">
                            <el-form-item :label="$t('register.basic.contact.phone')" prop="phone" required>
                                <el-input class="form-input" v-model="company.phone" :disabled="true" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item :label="$t('register.basic.contact.fax')" prop="fax">
                                <el-input class="form-input" v-model="company.fax" :disabled="true" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item :label="$t('register.basic.contact.email')" prop="email" required>
                                <el-input class="form-input" v-model="company.email" :disabled="true" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item :label="$t('register.basic.contact.website')" prop="website">
                                <el-input class="form-input" v-model="company.website" :disabled="true" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>

            </el-collapse-item>

            <el-collapse-item :title="'3. '+$t('register.business.title')" name="3">
                <el-divider content-position="left"><h4>{{ $t('register.business.title') }}</h4></el-divider>
                <el-form
                    ref="businessCategoryForm"
                    :disabled="true"
                    :model="businessCategory">

                    <el-form-item
                        prop="values">
                        <el-cascader-panel
                            ref="businessCategories"
                            v-model="businessCategory.values"
                            :value="businessCategory.values"
                            :options="businessCategoryOptions"
                            :props="{multiple: true}"
                        />
                    </el-form-item>
                </el-form>
            </el-collapse-item>

            <el-collapse-item :title="'4. '+$t('register.document.title')" name="4">
                <el-divider content-position="left"><h4>{{ $t('register.document.mandatory.title') }}</h4></el-divider>
                    <div class="table-container">
                        <el-table
                            ref="mainDocuments"
                            max-height="250"
                            style="width: 100%"
                            :data="mainDocuments"
                        >
                            <el-table-column
                                prop="typeName"
                                :label="$t('register.document.form.documentType')"
                            />
                            <el-table-column
                                prop="documentNo"
                                :label="$t('register.document.form.documentNo')"
                            />
                            <el-table-column
                                prop="expirationDate"
                                :label="$t('register.document.form.expirationDate')"
                            />
                            <el-table-column
                                prop="file.name"
                                :label="$t('register.document.form.file')"
                            />
                        </el-table>
                    </div>

                    <el-divider content-position="left"><h4>{{ $t('register.document.additional.title') }}</h4></el-divider>
                    <div class="table-container">
                        <el-table
                            ref="additionalDocuments"
                            max-height="250"
                            style="width: 100%"
                            :data="additionalDocuments"
                        >
                            <el-table-column
                                prop="typeName"
                                :label="$t('register.document.form.documentType')"
                            />
                            <el-table-column
                                prop="documentNo"
                                :label="$t('register.document.form.documentNo')"
                            />
                            <el-table-column
                                prop="expirationDate"
                                :label="$t('register.document.form.expirationDate')"
                            />
                            <el-table-column
                                prop="file.name"
                                :label="$t('register.document.form.file')"
                            />
                        </el-table>
                    </div>
            </el-collapse-item>

            <el-collapse-item :title="'5. '+$t('register.pic.title')" name="5">
                <el-divider content-position="left"><h4>{{ $t('register.pic.contact.title') }}</h4></el-divider>
                <div class="table-container">
                    <el-table
                        ref="contacts"
                        max-height="250"
                        style="width: 100%"
                        :data="contacts"
                    >
                        <el-table-column
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
                                <span>{{printBusinessCategory(row)}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            prop="userLogin"
                            min-width="128"
                            :label="$t('register.pic.username')"
                        />
                    </el-table>
                </div>

                <el-divider content-position="left"><h4>{{ $t('register.pic.functionary.title') }}</h4></el-divider>
                <div class="table-container">
                    <el-table
                        ref="functionaries"
                        max-height="250"
                        style="width: 100%"
                        :data="functionaries"
                    >
                        <el-table-column
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
                    </el-table>
                </div>
            </el-collapse-item>

            <el-collapse-item :title="'6. '+$t('register.payment.title')" name="6">
                <el-divider content-position="left"><h4>{{ $t('register.payment.title') }}</h4></el-divider>
                <div class="table-container">
                    <el-table
                        ref="payments"
                        max-height="250"
                        style="width: 100%"
                        :data="payments"
                    >
                        <el-table-column
                            min-width="128"
                            :label="$t('register.payment.bank')"
                        >
                            <template slot-scope="scope">
                                <span>{{printValueByParam(scope.row.bank)}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            prop="branch"
                            min-width="128"
                            :label="$t('register.payment.branch')"
                        />
                        <el-table-column
                            min-width="128"
                            :label="$t('register.payment.currency')"
                        >
                            <template slot-scope="scope">
                                <span>{{printValueByParam(scope.row.currency)}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            prop="account"
                            min-width="128"
                            :label="$t('register.payment.account')"
                        />
                        <el-table-column
                            prop="supportingfile.name"
                            min-width="128"
                            :label="$t('register.payment.supportingfile')"
                        />
                    </el-table>
                </div>
            </el-collapse-item>

            <el-collapse-item :title="'7. '+$t('register.tax.header.title')" name="7">
                <el-divider content-position="left"><h4>{{ $t('register.tax.header.title') }}</h4></el-divider>
                <el-form
                    ref="taxInformations"
                    label-position="left"
                    size="mini"
                    label-width="256px"
                >
                    <el-form-item :label="$t('register.tax.efaktur')">
                        <el-checkbox
                            v-model="eInvoice"
                            disabled
                            size="mini"
                        />
                    </el-form-item>
                    <el-form-item :label="$t('register.tax.pkp')">
                        <el-checkbox
                            v-model="taxableEmployers"
                            disabled
                            size="mini"
                        />
                    </el-form-item>
                </el-form>

                <el-divider content-position="left"><h4>{{ $t('register.tax.body.title') }}</h4></el-divider>
                <div class="table-container">
                    <el-table
                        max-height="250"
                        style="width: 100%"
                        :data="taxes">
                        <el-table-column
                            fixed
                            min-width="128"
                            prop="name"
                            :label="$t('register.tax.taxRateName')"/>
                        <el-table-column
                            prop="transactionType"
                            min-width="128"
                            :label="$t('register.tax.transactionType')"/>
                        <el-table-column
                            prop="rate"
                            min-width="128"
                            :label="$t('register.tax.rate')"/>
                    </el-table>
                </div>
            </el-collapse-item>

        </el-collapse>
        
    </div>
    
</template>
<script lang="ts" src="./summary-registration.component.ts"></script>
<style lang="scss" scoped>
    .summary {
        .el-form-item {
            margin-bottom: 0px;
        }
    }
</style>
