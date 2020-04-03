<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.vendor.home.createOrEditLabel" v-text="$t('opusWebApp.vendor.home.createOrEditLabel')">Create or edit a Vendor</h2>
                <div>
                    <div class="form-group" v-if="vendor.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="vendor.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.vendor.code')" for="vendor-code">Code</label>
                        <input type="text" class="form-control" name="code" id="vendor-code"
                            :class="{'valid': !$v.vendor.code.$invalid, 'invalid': $v.vendor.code.$invalid }" v-model="$v.vendor.code.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.vendor.name')" for="vendor-name">Name</label>
                        <input type="text" class="form-control" name="name" id="vendor-name"
                            :class="{'valid': !$v.vendor.name.$invalid, 'invalid': $v.vendor.name.$invalid }" v-model="$v.vendor.name.$model"  required/>
                        <div v-if="$v.vendor.name.$anyDirty && $v.vendor.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.vendor.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.vendor.npwp')" for="vendor-npwp">Npwp</label>
                        <input type="number" class="form-control" name="npwp" id="vendor-npwp"
                            :class="{'valid': !$v.vendor.npwp.$invalid, 'invalid': $v.vendor.npwp.$invalid }" v-model.number="$v.vendor.npwp.$model"  required/>
                        <div v-if="$v.vendor.npwp.$anyDirty && $v.vendor.npwp.$invalid">
                            <small class="form-text text-danger" v-if="!$v.vendor.npwp.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.vendor.npwp.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.vendor.branch')" for="vendor-branch">Branch</label>
                        <input type="checkbox" class="form-check" name="branch" id="vendor-branch"
                            :class="{'valid': !$v.vendor.branch.$invalid, 'invalid': $v.vendor.branch.$invalid }" v-model="$v.vendor.branch.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.vendor.email')" for="vendor-email">Email</label>
                        <input type="text" class="form-control" name="email" id="vendor-email"
                            :class="{'valid': !$v.vendor.email.$invalid, 'invalid': $v.vendor.email.$invalid }" v-model="$v.vendor.email.$model"  required/>
                        <div v-if="$v.vendor.email.$anyDirty && $v.vendor.email.$invalid">
                            <small class="form-text text-danger" v-if="!$v.vendor.email.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.vendor.phone')" for="vendor-phone">Phone</label>
                        <input type="text" class="form-control" name="phone" id="vendor-phone"
                            :class="{'valid': !$v.vendor.phone.$invalid, 'invalid': $v.vendor.phone.$invalid }" v-model="$v.vendor.phone.$model"  required/>
                        <div v-if="$v.vendor.phone.$anyDirty && $v.vendor.phone.$invalid">
                            <small class="form-text text-danger" v-if="!$v.vendor.phone.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.vendor.fax')" for="vendor-fax">Fax</label>
                        <input type="text" class="form-control" name="fax" id="vendor-fax"
                            :class="{'valid': !$v.vendor.fax.$invalid, 'invalid': $v.vendor.fax.$invalid }" v-model="$v.vendor.fax.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.vendor.website')" for="vendor-website">Website</label>
                        <input type="text" class="form-control" name="website" id="vendor-website"
                            :class="{'valid': !$v.vendor.website.$invalid, 'invalid': $v.vendor.website.$invalid }" v-model="$v.vendor.website.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.vendor.type')" for="vendor-type">Type</label>
                        <select class="form-control" name="type" :class="{'valid': !$v.vendor.type.$invalid, 'invalid': $v.vendor.type.$invalid }" v-model="$v.vendor.type.$model" id="vendor-type"  required>
                            <option value="COMPANY" v-bind:label="$t('opusWebApp.VendorType.COMPANY')">COMPANY</option>
                            <option value="PROFESSIONAL" v-bind:label="$t('opusWebApp.VendorType.PROFESSIONAL')">PROFESSIONAL</option>
                        </select>
                        <div v-if="$v.vendor.type.$anyDirty && $v.vendor.type.$invalid">
                            <small class="form-text text-danger" v-if="!$v.vendor.type.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.vendor.paymentCategory')" for="vendor-paymentCategory">Payment Category</label>
                        <select class="form-control" name="paymentCategory" :class="{'valid': !$v.vendor.paymentCategory.$invalid, 'invalid': $v.vendor.paymentCategory.$invalid }" v-model="$v.vendor.paymentCategory.$model" id="vendor-paymentCategory"  required>
                            <option value="RED" v-bind:label="$t('opusWebApp.PaymentCategory.RED')">RED</option>
                            <option value="GREEN" v-bind:label="$t('opusWebApp.PaymentCategory.GREEN')">GREEN</option>
                        </select>
                        <div v-if="$v.vendor.paymentCategory.$anyDirty && $v.vendor.paymentCategory.$invalid">
                            <small class="form-text text-danger" v-if="!$v.vendor.paymentCategory.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.vendor.approvalStatus')" for="vendor-approvalStatus">Approval Status</label>
                        <select class="form-control" name="approvalStatus" :class="{'valid': !$v.vendor.approvalStatus.$invalid, 'invalid': $v.vendor.approvalStatus.$invalid }" v-model="$v.vendor.approvalStatus.$model" id="vendor-approvalStatus"  required>
                            <option value="PENDING" v-bind:label="$t('opusWebApp.VendorApprovalStatus.PENDING')">PENDING</option>
                            <option value="REJECTED" v-bind:label="$t('opusWebApp.VendorApprovalStatus.REJECTED')">REJECTED</option>
                            <option value="APPROVED" v-bind:label="$t('opusWebApp.VendorApprovalStatus.APPROVED')">APPROVED</option>
                        </select>
                        <div v-if="$v.vendor.approvalStatus.$anyDirty && $v.vendor.approvalStatus.$invalid">
                            <small class="form-text text-danger" v-if="!$v.vendor.approvalStatus.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.vendor.location')" for="vendor-location">Location</label>
                        <select class="form-control" id="vendor-location" name="location" v-model="$v.vendor.locationId.$model" required>
                            <option v-if="!vendor.locationId" v-bind:value="null" selected></option>
                            <option v-bind:value="locationOption.id" v-for="locationOption in locations" :key="locationOption.id">{{locationOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.vendor.locationId.$anyDirty && $v.vendor.locationId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.vendor.locationId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                    <div class="form-group">
                        <label v-text="$t('opusWebApp.vendor.businessCategory')" for="vendor-businessCategory">Business Category</label>
                        <select class="form-control" id="vendor-businessCategory" multiple name="businessCategory" v-model="vendor.businessCategories">
                            <option v-bind:value="getSelected(vendor.businessCategories, businessCategoryOption)" v-for="businessCategoryOption in businessCategories" :key="businessCategoryOption.id">{{businessCategoryOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.vendor.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./vendor-update.component.ts">
</script>
