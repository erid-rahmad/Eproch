import { ADColumnType } from '@/shared/model/ad-column.model';
import { IADField } from '@/shared/model/ad-field.model';
import { isStringField } from "@/utils/validate";

export const buildCascaderOptions = (source: any[]) => {
  return source
    .reduce((p, currentItem, i, sourceArray) => {
      let parent = !!currentItem.parent && sourceArray.find(e => e.value === currentItem.parent);
      !!parent ? !!parent.children && parent.children.push(currentItem) || (parent.children = [currentItem]) : p.push(currentItem);
      return p;
    }, []);
};

export const normalizeField = (record: Record<string, any>, field: IADField) => {
  const fieldName = field.virtualColumnName || field.adColumn.name;
  const fieldType = field.type || field.adColumn.type;
  const isForeignKey = fieldName.endsWith('Id');

  if (! field.mandatory && ! field.adColumn?.mandatory) {
    const value = record[fieldName];
    const shouldNullify = (isForeignKey && !value) || (isStringField(field) && value?.trim() === '');
    if (shouldNullify) {
      record[fieldName] = null;
    }
  }

  if (fieldType === ADColumnType.LOCAL_DATE) {
    record[fieldName] = new Date(record[fieldName]);
  }
}
