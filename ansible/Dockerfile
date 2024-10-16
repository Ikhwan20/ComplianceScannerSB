FROM quay.io/ansible/ansible-runner:latest

# Set environment variables for Ansible
ENV ANSIBLE_HOST_KEY_CHECKING=False

# Install Python and Flask
RUN apt-get update && apt-get install -y --no-install-recommends \
    python3.5 \
    python3-pip \
    flask \   
    && \
    apt-get clean

# Copy playbooks and vault password
COPY playbooks /runner/project/playbooks/
COPY playbooks/vault_pass.txt /runner/secrets/vault_pass.txt

# Copy the API server script
COPY api_server.py /api_server.py

# Set permissions for the vault password file
RUN chmod 600 /runner/secrets/vault_pass.txt

# Expose the API server port
EXPOSE 5000

# Start the API server
CMD ["python3", "/api_server.py"]

