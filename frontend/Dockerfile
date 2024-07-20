# Use official node image as the base image
FROM node:20-alpine as build

# Set the working directory
WORKDIR /usr/local/app

# Add the source code to app
COPY ./ /usr/local/app/

# Add your nginx.conf
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Install all the dependencies
RUN npm install --legacy-peer-deps

# Generate the build of the application
RUN npm run build


# Use official nginx image as the base image
FROM nginx:latest

# Copy the build output to replace the default nginx contents.
COPY --from=build /usr/local/app/dist/frontend /usr/share/nginx/html
COPY --from=build /usr/local/app/nginx.conf /etc/nginx/conf.d/default.conf

CMD ["nginx", "-g", "daemon off;"]