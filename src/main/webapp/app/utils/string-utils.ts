export const generateRandomString = (length = 6) => Math.random().toString(20).substr(2, length);

export const formatNotificationTitle = (processType: string) => {
  let title;
  if (processType) {
    title = `${processType.substring(0, 1)}${processType.substring(1).toLowerCase()} Status`;
  } else {
    title = 'Backround Process Status';
  }
  return title;
}
