<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.cRegion.home.createOrEditLabel" v-text="$t('opusWebApp.cRegion.home.createOrEditLabel')">Create or edit a CRegion</h2>
                <div>
                    <div class="form-group" v-if="cRegion.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cRegion.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cRegion.name')" for="c-region-name">Name</label>
                        <input type="text" class="form-control" name="name" id="c-region-name"
                            :class="{'valid': !$v.cRegion.name.$invalid, 'invalid': $v.cRegion.name.$invalid }" v-model="$v.cRegion.name.$model"  required/>
                        <div v-if="$v.cRegion.name.$anyDirty && $v.cRegion.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.cRegion.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cRegion.active')" for="c-region-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="c-region-active"
                            :class="{'valid': !$v.cRegion.active.$invalid, 'invalid': $v.cRegion.active.$invalid }" v-model="$v.cRegion.active.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.cRegion.country')" for="c-region-country">Country</label>
                        <select class="form-control" id="c-region-country" name="country" v-model="$v.cRegion.countryId.$model" required>
                            <option v-if="!cRegion.countryId" v-bind:value="null" selected></option>
                            <option v-bind:value="cCountryOption.id" v-for="cCountryOption in cCountries" :key="cCountryOption.id">{{cCountryOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.cRegion.countryId.$anyDirty && $v.cRegion.countryId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.cRegion.countryId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cRegion.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./c-region-update.component.ts">
</script>
