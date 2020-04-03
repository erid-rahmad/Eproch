<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.personInCharge.home.createOrEditLabel" v-text="$t('opusWebApp.personInCharge.home.createOrEditLabel')">Create or edit a PersonInCharge</h2>
                <div>
                    <div class="form-group" v-if="personInCharge.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="personInCharge.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.personInCharge.position')" for="person-in-charge-position">Position</label>
                        <input type="text" class="form-control" name="position" id="person-in-charge-position"
                            :class="{'valid': !$v.personInCharge.position.$invalid, 'invalid': $v.personInCharge.position.$invalid }" v-model="$v.personInCharge.position.$model"  required/>
                        <div v-if="$v.personInCharge.position.$anyDirty && $v.personInCharge.position.$invalid">
                            <small class="form-text text-danger" v-if="!$v.personInCharge.position.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.personInCharge.phone')" for="person-in-charge-phone">Phone</label>
                        <input type="text" class="form-control" name="phone" id="person-in-charge-phone"
                            :class="{'valid': !$v.personInCharge.phone.$invalid, 'invalid': $v.personInCharge.phone.$invalid }" v-model="$v.personInCharge.phone.$model"  required/>
                        <div v-if="$v.personInCharge.phone.$anyDirty && $v.personInCharge.phone.$invalid">
                            <small class="form-text text-danger" v-if="!$v.personInCharge.phone.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.personInCharge.user')" for="person-in-charge-user">User</label>
                        <select class="form-control" id="person-in-charge-user" name="user" v-model="$v.personInCharge.userId.$model" required>
                            <option v-if="!personInCharge.userId" v-bind:value="null" selected></option>
                            <option v-bind:value="userOption.id" v-for="userOption in users" :key="userOption.id">{{userOption.login}}</option>
                        </select>
                    </div>
                    <div v-if="$v.personInCharge.userId.$anyDirty && $v.personInCharge.userId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.personInCharge.userId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.personInCharge.vendor')" for="person-in-charge-vendor">Vendor</label>
                        <select class="form-control" id="person-in-charge-vendor" name="vendor" v-model="personInCharge.vendorId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="vendorOption.id" v-for="vendorOption in vendors" :key="vendorOption.id">{{vendorOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.personInCharge.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./person-in-charge-update.component.ts">
</script>
